package com.hb.rimi.angel.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.MainActivity;
import com.hb.rimi.angel.adapter.GroupNoaddAdapter;
import com.hb.rimi.angel.view.PrgDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupInfo;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.NetUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 发现
 */
@ContentView(R.layout.fragment_find)
public class FindFragment extends Fragment implements EMConnectionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    @Bind(R.id.groupName)
    ListView groupName;
    @Bind(R.id.groupedAddedName)
    ListView groupedAddedName;
    @Bind(R.id.noQuanzi)
    LinearLayout noQuanzi;
    @Bind(R.id.tv_msg)
    TextView tvMsg;
    @Bind(R.id.btn_flash)
    Button btnFlash;
    private String mParam1;
    private String mParam2;

    private List<EMGroupInfo> groupsList;
    private List<EMGroup> grouplist;
    private GroupNoaddAdapter groupNoaddAdapter;


    private OnFragmentInteractionListener mListener;


    public FindFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindFragment.
     */
    public static FindFragment newInstance(String param1, String param2) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:

                    //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                    try {
                        //需异步处理
                        grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    //从本地加载群组列表
                    grouplist = EMClient.getInstance().groupManager().getAllGroups();
                    groupNoaddAdapter = new GroupNoaddAdapter(grouplist, getContext());
                    groupName.setAdapter(groupNoaddAdapter);
                    break;
            }
            return true;
        }
    });
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
        EMClient.getInstance().login("mei", "654321", new EMCallBack() {
            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        //Toast.makeText(getContext(), "登录聊天服务器成功", Toast.LENGTH_LONG).show();
                        GroupInfoTask task = new GroupInfoTask(getContext());
                        task.execute();
                        //tvMsg.setText("登录聊天服务器成功");

                        /*try {
                            //需异步处理
                            grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }

                        //从本地加载群组列表
                        grouplist = EMClient.getInstance().groupManager().getAllGroups();
                        groupNoaddAdapter = new GroupNoaddAdapter(grouplist, getContext());
                        groupName.setAdapter(groupNoaddAdapter);
                        //获取公开群列表*/
                    }
                });

            }

            @Override
            public void onError(int i, String s) {
                //Toast.makeText(getContext(), "登录聊天服务器失败", Toast.LENGTH_LONG).show();
                //tvMsg.setText("登录聊天服务器失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        EMClient.getInstance().addConnectionListener(this);
    }

    @Override
    public void onConnected() {
        //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
        try {
            //需异步处理
            grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }

        //从本地加载群组列表
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupNoaddAdapter = new GroupNoaddAdapter(grouplist, getContext());
        groupName.setAdapter(groupNoaddAdapter);
        //获取公开群列表
        //pageSize为要取到的群组的数量，cursor用于告诉服务器从哪里开始取
        //EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(6, null);//需异步处理
        //groupsList = result.getData();
        //String cursor = result.getCursor();
    }

    @Override
    public void onDisconnected(final int error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                    //Toast.makeText(getContext(), "账号已被移除", Toast.LENGTH_SHORT).show();
                    //tvMsg.setText("账号已被移除");
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    //Toast.makeText(getContext(), "账号在其他设备登录", Toast.LENGTH_SHORT).show();
                    //tvMsg.setText("账号在其他设备登录");
                } else {
                    if (NetUtils.hasNetwork(getContext())) {
                        //连接不到聊天服务器
                        //Toast.makeText(getContext(), "链接不到聊天服务器", Toast.LENGTH_SHORT).show();
                        //tvMsg.setText("链接不到聊天服务器");
                    } else {
                        //当前网络不可用，请检查网络设置
                        //Toast.makeText(getContext(), "网络不可用", Toast.LENGTH_SHORT).show();
                        //tvMsg.setText("网络不可用");
                    }

                }
            }
        });
    }

    private void initView(View view) {

    }


    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class GroupInfoTask extends AsyncTask<Void, Void, List<EMGroup>> {
        Context context;
        private PrgDialog dialog;
        private int pageSize = 6;//每页数量
        private String cursor = null;//从什么地方开始取

        public GroupInfoTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new PrgDialog(context);
        }

        @Override
        protected List<EMGroup> doInBackground(Void... params) {
            try {
                List<EMGroup> emGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                /*EMCursorResult<EMGroup> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(pageSize, cursor);
                List<EMGroupInfo> groupInfos = result.getData();*/
                //String cursor = emGroups.getCursor();
                Log.d("info", "cursor=============》" + cursor);
                return emGroups;
            } catch (HyphenateException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<EMGroup> groupInfos) {
            super.onPostExecute(groupInfos);
            GroupNoaddAdapter appAdapter = new GroupNoaddAdapter(groupInfos,context);
            groupName.setAdapter(appAdapter);
            appAdapter.notifyDataSetChanged();
            dialog.closeDialog();
        }
    }
}



