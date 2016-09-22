package com.hb.rimi.angel.activity;


import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hb.findim.FindNewFragment;
import com.hb.findim.adapter.GroupAdapter;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.fragment.FindFragment;
import com.hb.rimi.angel.fragment.HomeFragment;
import com.hb.rimi.angel.fragment.MineFragment;
import com.hb.rimi.angel.fragment.ShopFragment;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.ViewIndicator;
import com.hb.update.UpdateManager;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.db.InviteMessgeDao;
import com.hyphenate.chatuidemo.db.UserDao;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsManager;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsResultAction;
import com.hyphenate.util.EMLog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, FindFragment.OnFragmentInteractionListener, MineFragment.OnFragmentInteractionListener, ShopFragment.OnFragmentInteractionListener {

    private static int defaultWhich = 0;

    private static String TAG = MainActivity.class.getSimpleName();
    // 账号在别处登录
    public boolean isConflict = false;
    private Fragment homeFragment, myFragment;
    public ShopFragment shopFragment;
    private FindNewFragment findFragment;// findFragment,
    //    private NewFindFragment findFragment;// findFragment,
    private ViewIndicator ma_viewindicator;
    private int positinId;//关闭应用时的Fragment ID 防止UI重叠
    private Context mContext;

    //以下为环信相关
    private InviteMessgeDao inviteMessgeDao;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private int currentTabIndex;
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // 提示新消息
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }


        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                final String action = cmdMsgBody.action();//获取自定义action
//                if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION) && message.getChatType() == EMMessage.ChatType.GroupChat) {
//                    RedPacketUtil.receiveRedPacketAckMessage(message);
//                }
            }
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            System.out.println("onMessageReadAckReceived==========>");
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            System.out.println("onMessageDeliveryAckReceived==========>");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            System.out.println("onMessageChanged==========>");
        }

    };
    private UserDao userDao;
    private long exitTime = 0;
    private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); //透明导航栏
        }
        setContentView(R.layout.activity_main);

        //text = getIntent().getStringExtra("text")+"";
        if (getIntent().getStringExtra("text") != null) {
            text = getIntent().getStringExtra("text") + "";
            T.ShowToast(mContext, "==>" + text);
        }

        //环信1
        requestPermissions();
        mContext = MainActivity.this;
        initView();
        //环信2
        hyInit();
        //检测是否有新版本
        checkUpdate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void checkUpdate() {
        if (ShareInfoUtil.readIsNewVer(mContext)) {
            UpdateManager.newUpdate(mContext);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                T.ShowToast(MainActivity.this, "再按一次退出", 0);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                ShareInfoUtil.saveParams(this, "appstate", "0");
            }
            return true;
        }
        return false;
    }

    private void hyInit() {
        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
        userDao = new UserDao(this);
        inviteMessgeDao = new InviteMessgeDao(mContext);
        initData();
        initBottomMenu();
        //注册local广播接收者，用于接收demohelper中发出的群组联系人的变动通知
        registerBroadcastReceiver();
    }

    private void initView() {
        ma_viewindicator = (ViewIndicator) findViewById(R.id.ma_viewindicator);
    }

    private void initData() {
        homeFragment = new HomeFragment();
        //findFragment = new FindFragment();//old
//        findFragment = new NewFindFragment();//new 来自库的发现
        findFragment = new FindNewFragment();//new2 来自新库的发现
        shopFragment = new ShopFragment();
        myFragment = new MineFragment();


        Bundle bundle = new Bundle();
        bundle.putString("easemobToken", ShareInfoUtil.readEasemobToken(mContext));
        bundle.putString("easemobTokenPsd", com.hb.rimi.angel.contanst.Contanst.hxPsd);
        bundle.putString("url", HttpContanst.GROUPCHAT + ShareInfoUtil.readToken(mContext));
        bundle.putString("token", ShareInfoUtil.readToken(mContext));
        findFragment.setArguments(bundle);

        //Bundle bundle2 = getIntent().getExtras();
        //String text = bundle2.getString("text");
        Bundle bundle3 = new Bundle();
        System.out.println("==bun=>" + text);
        bundle3.putString("text", text);
        shopFragment.setArguments(bundle3);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame_layout, homeFragment);
        fragmentTransaction.add(R.id.main_frame_layout, findFragment);
        fragmentTransaction.add(R.id.main_frame_layout, shopFragment);
        fragmentTransaction.add(R.id.main_frame_layout, myFragment);
        fragmentTransaction.commit();
        showFragment(defaultWhich);
    }

    private void initBottomMenu() {
        ViewIndicator.setIndicator(defaultWhich);
        ma_viewindicator.setOnIndicateListener(new ViewIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                showFragment(which);
            }
        });
    }

    private void showFragment(int which) {
        positinId = which;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.hide(findFragment);
        fragmentTransaction.hide(shopFragment);
        fragmentTransaction.hide(myFragment);
        if (shopFragment.popupWindow != null&&shopFragment.popupWindow.isShowing()) {
            shopFragment.popupWindow.dismiss();
        }
        switch (which) {
            case 0:
                fragmentTransaction.show(homeFragment);
                break;
            case 1:
                fragmentTransaction.show(findFragment);
                break;
            case 2:
                fragmentTransaction.show(shopFragment);
                break;
            case 3:
                fragmentTransaction.show(myFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * F与A通信
     *
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * 下面两个方法解决缓存应用回来后防止Fragment重叠
     * positinId 关闭应用时的Fragment ID
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        positinId = savedInstanceState.getInt("positinId");
        showFragment(positinId);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //记录当前的position
        outState.putInt("positinId", positinId);
    }

    /*******************
     * 环信*********************、
     */

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(com.hb.findim.R.string.Logoff_notification);
        if (!isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(com.hb.findim.R.string.connect_conflict);
                conflictBuilder.setPositiveButton(com.hb.findim.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
//
//                        finish();
//                        Intent intent = new Intent(mAct, FindHomeActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }

        }

    }

    /**
     * 帐号被移除的dialog
     */
    private void showAccountRemovedDialog() {
        isAccountRemovedDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st5 = getResources().getString(com.hb.findim.R.string.Remove_the_notification);
        if (!isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(com.hb.findim.R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(com.hb.findim.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
//                        startActivity(new Intent(mAct, FindHomeActivity.class));
                    }
                });
                accountRemovedBuilder.setCancelable(false);
                accountRemovedBuilder.create().show();
                isCurrentAccountRemoved = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color userRemovedBuilder error" + e.getMessage());
            }

        }

    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
//        intentFilter.addAction(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLabel();
                if (currentTabIndex == 0) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (findFragment != null) {
                        findFragment.refresh();

                    }
                }
//                else if (currentTabIndex == 1) {
//                    if (contactListFragment != null) {
//                        contactListFragment.refresh();
//                    }
//                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
//                    contactRefresh();//刷新组
//                    if (EaseCommonUtils.getTopActivity(FindHomeActivity.this).equals(GroupsActivity.class.getName())) {
//                        GroupsActivity.instance.onResume();
//                    }
                }
//                if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//                    if (conversationListFragment != null) {
//                        conversationListFragment.refresh();
//                    }
//                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
//            unreadLabel.setText(String.valueOf(count));
//            unreadLabel.setVisibility(View.VISIBLE);
        } else {
//            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        for (EMConversation conversation : EMClient.getInstance().chatManager().getAllConversations().values()) {
            if (conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount = chatroomUnreadMsgCount + conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal - chatroomUnreadMsgCount;
    }

    private void refreshUIWithMessage() {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                // 刷新bottom bar消息未读数
//                updateUnreadLabel();
//                if (currentTabIndex == 0) {
//                    // 当前页面如果为聊天历史页面，刷新此页面
//                    if (findFragment != null) {
//                        findFragment.refresh();
//                    }
//                }
//            }
//        });

        new Thread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (currentTabIndex == 0) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (findFragment != null) {
                        findFragment.refresh();
                    }
                }
            }
        }).start();
    }

    /**
     * 获取未读申请与通知消息
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }

    /**
     * 刷新申请与通知消息数
     */
    public void updateUnreadAddressLabel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
//					unreadAddressLable.setText(String.valueOf(count));
//                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
//                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        }).start();
//        runOnUiThread(new Runnable() {
//            public void run() {
//                int count = getUnreadAddressCountTotal();
//                if (count > 0) {
////					unreadAddressLable.setText(String.valueOf(count));
////                    unreadAddressLable.setVisibility(View.VISIBLE);
//                } else {
////                    unreadAddressLable.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

    }

    //TODO 刷新组 暂未实现
    public void contactRefresh() {
        findFragment.groups = EMClient.getInstance().groupManager().getAllGroups();
        findFragment.groupAdapter = new GroupAdapter(mContext, findFragment.groups);
        findFragment.fha_recycle_conversation.setAdapter(findFragment.groupAdapter);
        findFragment.groupAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        //T.ShowToast(MainActivity.this,"12334546");
        //Bundle bundle = getIntent().getExtras();
        /*if(getIntent().getExtras().getString("text")!=null){
            text = getIntent().getExtras().getString("text");
            T.ShowToast(mContext,"==>"+text);
        }*/
        /*int id = getIntent().getIntExtra("id",0);
        if(id == 2){
            String text = getIntent().getStringExtra("text");
            System.out.println("==text==>"+text+"  "+id);
            T.ShowToast(mContext,text);
            Bundle bundle = new Bundle();
            bundle.putString("text",text);
            shopFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(findFragment);
            fragmentTransaction.hide(myFragment);
            fragmentTransaction.show(shopFragment);

            fragmentTransaction.commit();
        }*/
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLabel();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }
        unregisterBroadcastReceiver();
    }
}
