package com.hb.findim;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.findim.adapter.GroupAdapter;
import com.hb.findim.bean.ResHxGroup;
import com.hb.findim.http.UpdateRequest;
import com.hb.findim.util.DividerItemDecoration;
import com.hb.findim.view.PrgDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.db.InviteMessgeDao;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsManager;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsResultAction;
import com.hyphenate.chatuidemo.ui.ConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TXG 2016-06-11
 */
public class FindHomeActivity extends AppCompatActivity {
    private RecyclerView fha_recycle_conversation;
    private RecyclerView fha_recycle_public;
    private PrgDialog dialog;
    private Context mContext;
    private List<EMGroup> groups;//私有列表

    private int pageSize = 20;
    private String cursor;
    private TextView unreadLabel;
    private TextView unreadAddressLable;
    private FrameLayout fha_fl;//添加显示会话列表
    private ConversationListFragment conversationListFragment;//会话列表
    private InviteMessgeDao inviteMessgeDao;


    // 账号在别处登录
    public boolean isConflict = false;
    // 账号被移除
    private boolean isCurrentAccountRemoved = false;
    private boolean isConflictDialogShow;
    private android.app.AlertDialog.Builder conflictBuilder;
    private boolean isAccountRemovedDialogShow;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;
    // 当前fragment的index
    private int currentTabIndex;
    private GroupAdapter groupAdapter;

    private static String TAG = FindHomeActivity.class.getSimpleName();
    private List<ResHxGroup.ResultBean> resHxGroupList = new ArrayList<>();
    private String hxResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fi_activity_find_home);
        //6.0运行时权限处理，target api设成23时，demo这里做的比较简单，直接请求所有需要的运行时权限
        requestPermissions();
        initView();

        if (getIntent().getBooleanExtra(Constant.ACCOUNT_CONFLICT, false) && !isConflictDialogShow) {
            showConflictDialog();
        } else if (getIntent().getBooleanExtra(Constant.ACCOUNT_REMOVED, false) && !isAccountRemovedDialogShow) {
            showAccountRemovedDialog();
        }
        inviteMessgeDao = new InviteMessgeDao(this);
        initData();
        //注册local广播接收者，用于接收demohelper中发出的群组联系人的变动通知

        registerBroadcastReceiver();
    }

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

    private void initView() {
        fha_recycle_conversation = (RecyclerView) findViewById(R.id.fha_recycle_conversation);
        fha_recycle_public = (RecyclerView) findViewById(R.id.fha_recycle_public);
        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        fha_fl = (FrameLayout) findViewById(R.id.fha_fl);
    }

    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!FindHomeActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(FindHomeActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        finish();
//                        Intent intent = new Intent(FindHomeActivity.this, FindHomeActivity.class);
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
        String st5 = getResources().getString(R.string.Remove_the_notification);
        if (!FindHomeActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (accountRemovedBuilder == null)
                    accountRemovedBuilder = new android.app.AlertDialog.Builder(FindHomeActivity.this);
                accountRemovedBuilder.setTitle(st5);
                accountRemovedBuilder.setMessage(R.string.em_user_remove);
                accountRemovedBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        accountRemovedBuilder = null;
                        finish();
//                        startActivity(new Intent(FindHomeActivity.this, FindHomeActivity.class));
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

    private void initData() {
        mContext = FindHomeActivity.this;
        //设置recycelerView的布局管理器
        fha_recycle_public.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        fha_recycle_public.addItemDecoration(new DividerItemDecoration(this));
        //设置recycelerView的布局管理器
        fha_recycle_conversation.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        fha_recycle_conversation.addItemDecoration(new DividerItemDecoration(this));
        conversationListFragment = new ConversationListFragment();
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fha_fl, conversationListFragment)
                .show(conversationListFragment)
                .commit();

        // 测试账号  258369 123456   进行登陆  非群主qwert  123456
        EMClient.getInstance().login("258369", "123456", new EMCallBack() {
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        Toast.makeText(FindHomeActivity.this, "登录聊天服务器成功", Toast.LENGTH_SHORT).show();

                        GroupInfoTask task = new GroupInfoTask();
                        task.execute();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "登录聊天服务器失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    class GroupInfoTask extends AsyncTask<Void, Void, List<ResHxGroup.ResultBean>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new PrgDialog(mContext);
        }

        @Override
        protected List<ResHxGroup.ResultBean> doInBackground(Void... params) {
//            try {
//                EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(pageSize, cursor);
//                List<EMGroupInfo> groupInfos = result.getData();
//                String cursor = result.getCursor();
//                Log.d("info", "cursor=============》" + cursor);

                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    //需异步处理
                    groups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                List<NameValuePair> nvp = new ArrayList<NameValuePair>();
                NameValuePair nvpToken = new BasicNameValuePair("token",
                        "");
                nvp.add(nvpToken);
                hxResult = UpdateRequest.sendPost(nvp, "");
                //得到扩展组列表

                try {
                    JSONObject jsonObject = new JSONObject(hxResult);
                    int status = jsonObject.getInt("status");
                    if (status == 0) {
                        JSONArray jsonArray = new JSONArray(jsonObject.getString("result"));
                        ResHxGroup.ResultBean resHxGroup;
                        if (null != jsonArray) {
                            int len = jsonArray.length();
                            for (int i = 0; i < len; i++) {
                                JSONObject jObject = jsonArray.getJSONObject(i);
                                resHxGroup = new ResHxGroup.ResultBean();
                                String img = jObject.getString("img");
                                String groupid = jObject.getString("groupid");
                                String affiliations = jObject.getString("affiliations");
                                String description = jObject.getString("description");
                                String groupname = jObject.getString("groupname");


                                resHxGroup.setImg(img);
                                resHxGroup.setGroupid(groupid);
                                resHxGroup.setAffiliations(affiliations);
                                resHxGroup.setDescription(description);
                                resHxGroup.setGroupname(groupname);

                                resHxGroupList.add(resHxGroup);

                            }
                        } else {
                            Toast.makeText(mContext, "暂无群组列表", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, "获取群组列表失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return resHxGroupList;
//            } catch (HyphenateException e) {
//                e.printStackTrace();
//                return null;
//            }
        }

        @Override
        protected void onPostExecute(List<ResHxGroup.ResultBean> groupInfos) {
            super.onPostExecute(groupInfos);
            dialog.closeDialog();
            //填充私有群
            groupAdapter = new GroupAdapter(mContext, groups);
            fha_recycle_conversation.setAdapter(groupAdapter);
            groupAdapter.notifyDataSetChanged();
            //填充公开群
//            GroupInfoAdapter appAdapter = new GroupInfoAdapter(mContext, groupInfos,conversationListFragment,Object);
//            fha_recycle_public.setAdapter(appAdapter);
//            appAdapter.notifyDataSetChanged();

        }
    }

    /**
     * 刷新申请与通知消息数
     */
    public void updateUnreadAddressLable() {
        runOnUiThread(new Runnable() {
            public void run() {
                int count = getUnreadAddressCountTotal();
                if (count > 0) {
//					unreadAddressLable.setText(String.valueOf(count));
                    unreadAddressLable.setVisibility(View.VISIBLE);
                } else {
                    unreadAddressLable.setVisibility(View.INVISIBLE);
                }
            }
        });

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
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {

        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
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

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
//        intentFilter.addAction(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();
                if (currentTabIndex == 0) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
//                else if (currentTabIndex == 1) {
//                    if (contactListFragment != null) {
//                        contactListFragment.refresh();
//                    }
//                }
                String action = intent.getAction();
                if (action.equals(Constant.ACTION_GROUP_CHANAGED)) {
                    contactRefresh();//刷新组
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

    public void contactRefresh() {
        groups = EMClient.getInstance().groupManager().getAllGroups();
        groupAdapter = new GroupAdapter(mContext, groups);
        fha_recycle_conversation.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }

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
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (currentTabIndex == 0) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    if (conversationListFragment != null) {
                        conversationListFragment.refresh();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict && !isCurrentAccountRemoved) {
            updateUnreadLabel();
            updateUnreadAddressLable();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.popActivity(this);

        super.onStop();
    }
}
