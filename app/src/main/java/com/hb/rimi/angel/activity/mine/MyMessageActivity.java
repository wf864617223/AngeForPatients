package com.hb.rimi.angel.activity.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.MyMessageAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.MessageList;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyMessageActivity extends AppCompatActivity {


    @Bind(R.id.lv_mymessage)
    ListView lvMymessage;
    @Bind(R.id.tv_no_message)
    TextView tvNoMessage;
    @Bind(R.id.mymessage_toolbar)
    Toolbar mymessageToolbar;

    private Context mContext;
    private List<MessageList.ResultBean.ListBean> list = new ArrayList<>();
    private MyMessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
        //getSupportActionBar().hide();
        mContext = MyMessageActivity.this;
        initBar();
        initView();
    }

    private void initBar() {
        //此处设置标题栏，否则在其它位置无效
        mymessageToolbar.setTitle("");
        setSupportActionBar(mymessageToolbar);
        mymessageToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        mymessageToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mymessageToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();// 这是自定义的代码
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_clean_message, menu);
        return true;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            lvMymessage.setVisibility(View.GONE);
            tvNoMessage.setVisibility(View.VISIBLE);
            tvNoMessage.setText("暂无消息");
            return false;
        }
    });
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clean_message) {
            if (lvMymessage == null || list.size() == 0) {
                T.ShowToast(mContext, "没有可清空的消息");
            } else {
                new AlertDialog.Builder(mContext).setTitle("清空消息").setMessage("是否清空所有消息？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String token = ShareInfoUtil.readToken(mContext);
                                HashMap<String,String> param = new HashMap<>();
                                param.put("token",token);
                                param.put("all","true");
                                HttpUtil.doHttp(HttpContanst.DELETE_MESSAGE, param, new HttpUtil.IHttpResult() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            int status = jsonObject.getInt("status");
                                            String message = jsonObject.getString("message");
                                            if (status == 0 || "success".equals(message)) {
                                                T.ShowToast(mContext, "已清空");
                                                Message msg = new Message();
                                                msg.what = 1;
                                                handler.sendMessage(msg);
                                            }else{
                                                T.ShowToast(mContext,"清空失败，服务器出错");

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {
                                        T.ShowToast(mContext, "请求失败，请稍后再试");
                                    }
                                });
                            }
                        })
                        .setNegativeButton("否", null).show();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        String token = ShareInfoUtil.readToken(mContext);
        HttpUtil.doHttp(HttpContanst.MESSAGE_LIST + "token=" + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                MessageList messageList = GsonTools.getBean(result, MessageList.class);
                int status = messageList.getStatus();
                String message = messageList.getMessage();
                if (status == 0 || "success".equals(message)) {
                    MessageList.ResultBean result1 = messageList.getResult();
                    list = result1.getList();
                    adapter = new MyMessageAdapter(list, mContext);
                    lvMymessage.setAdapter(adapter);
                    if (list.size() == 0 || lvMymessage == null) {
                        tvNoMessage.setVisibility(View.VISIBLE);
                    }
                } else {
                    T.ShowToast(mContext, message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                exit();// 这是自定义的代码
            }
            return true;
        }
        return super.dispatchKeyEvent(event);

    }

    public void exit() {
        if ("1".equals(ShareInfoUtil.readParams(this, "appstate"))) {
            System.out.println("info----------------1");
            finish();

        } else if ("0".equals(ShareInfoUtil.readParams(this, "appstate"))) {
            System.out.println("info----------------0");
            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage(
                            "com.hb.rimi.angel");
            startActivity(LaunchIntent);
            finish();
        } else {
            System.out.println("info----------------else");
            finish();
        }
    }
}
