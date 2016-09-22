package com.hb.findim;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hb.findim.adapter.GroupAdapter;
import com.hb.findim.adapter.GroupInfoAdapter;
import com.hb.findim.bean.ResHxGroup;
import com.hb.findim.http.UpdateRequest;
import com.hb.findim.util.DividerItemDecoration;
import com.hb.findim.view.PrgDialog;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chatuidemo.ui.ConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 基于环信EsayUI进行再次封装的Fragment
 * Created by rimi on 2016/6/13.
 */
public class FindNewFragment extends ConversationListFragment implements UpdateListInterface {

    private static String TAG = FindNewFragment.class.getSimpleName();
    public RecyclerView fha_recycle_conversation;
    public List<EMGroup> groups;//私有列表
    public GroupAdapter groupAdapter;
    public GroupInfoAdapter appAdapter;
    //从avtivity传递过来的参数
    String easemobToken = "";
    String easemobTokenPsd = "";
    String url = "";
    String token = "";
    private String hxResult = "";


    private View view;
    private Context mContext;
    private Activity mAct;
    private RecyclerView fha_recycle_public;
    private PrgDialog dialog;
    private int pageSize = 20;
    private String cursor;
    private FrameLayout fha_fl;//添加显示会话列表
    private ConversationListFragment conversationListFragment;//会话列表

    private List<ResHxGroup.ResultBean> resHxGroupList = new ArrayList<>();

    private boolean isFrist = false;//是否第一次
    public static boolean isExit = false;//是否按下了退出
    private boolean isPouse = false;//是否暂停


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mAct = getActivity();
        isFrist = true;

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            easemobToken = bundle.getString("easemobToken");
            easemobTokenPsd = bundle.getString("easemobTokenPsd");
            url = bundle.getString("url");
            token = bundle.getString("token");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initData() {
        //设置recycelerView的布局管理器
        fha_recycle_public.setLayoutManager(new LinearLayoutManager(mContext));
        //添加默认分割线
        fha_recycle_public.addItemDecoration(new DividerItemDecoration(mContext));
        //设置recycelerView的布局管理器
        fha_recycle_conversation.setLayoutManager(new LinearLayoutManager(mContext));
        //添加默认分割线
        fha_recycle_conversation.addItemDecoration(new DividerItemDecoration(mContext));
        //启动获取分组列表
        EMClient.getInstance().login(easemobToken, easemobTokenPsd, new EMCallBack() {
            @Override
            public void onSuccess() {
                mAct.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        System.out.println("环信登录成功");
                        //加载列表
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

    private void initViewMain(View view) {
        fha_recycle_conversation = (RecyclerView) view.findViewById(R.id.fha_recycle_conversation);
        fha_recycle_public = (RecyclerView) view.findViewById(R.id.fha_recycle_public);
        fha_fl = (FrameLayout) view.findViewById(R.id.fha_fl);
    }


    @Override
    protected void setUpView() {
        super.setUpView();
        initViewMain(view);
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        view = View.inflate(mAct, R.layout.fi_activity_find_home, null);
        fl_conversion.addView(view);
    }

    /**
     * 刷新页面
     */
    @Override
    public void refresh() {
        super.refresh();
    }

    /**
     * 更新UI
     */
    @Override
    public void update() {
        //加载列表
        GroupInfoTask task = new GroupInfoTask();
        task.execute();
    }

    /**
     * 请求公开群列表
     */
    class GroupInfoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new PrgDialog(mContext);

            if (isFrist == true && resHxGroupList != null && resHxGroupList.size() > 0) {
                resHxGroupList.clear();
            }
            if (groups != null && groups.size() > 0) {
                groups.clear();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            System.out.println("GroupInfoTask");
            //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
            try {
                //需异步处理
                groups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
            } catch (HyphenateException e) {
                e.printStackTrace();
                groups = EMClient.getInstance().groupManager().getAllGroups();
            }
            if (isFrist == true) {
                List<NameValuePair> nvp = new ArrayList<NameValuePair>();
                NameValuePair nvpToken = new BasicNameValuePair("token",
                        token);
                nvp.add(nvpToken);
                hxResult = UpdateRequest.sendPost(nvp, url);
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
                            System.out.println("暂无群组列表");
                        }
                    } else {
                        System.out.println("获取群组列表失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("GroupInfoTask2");
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            isFrist = false;
            isPouse = false;
            isExit = false;
            dialog.closeDialog();
            //填充私有群
            System.out.println("onPostExecute");
//            if (groupAdapter == null) {
            groupAdapter = new GroupAdapter(mContext, groups);
            fha_recycle_conversation.setAdapter(groupAdapter);
//            }
            groupAdapter.notifyDataSetChanged();
            //去重复数据

            if (resHxGroupList != null && resHxGroupList.size() > 0) {
                for (EMGroup gr : groups) {
                    Iterator<ResHxGroup.ResultBean> it = resHxGroupList.iterator();
                    while (it.hasNext()) {
                        if (gr.getGroupId().equals(it.next().getGroupid())) {
                            it.remove();
//                            break;
                        }
                        continue;
                    }
                }
            }
            //填充公开群
//            if (appAdapter == null) {
            appAdapter = new GroupInfoAdapter(mContext, resHxGroupList, FindNewFragment.this);
            fha_recycle_public.setAdapter(appAdapter);
//            }
            appAdapter.notifyDataSetChanged();
            System.out.println("onPostExecute2");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isPouse = true && isExit == true) {
            isFrist = true;
            isExit = false;
            update();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        isPouse = true;
    }
}

