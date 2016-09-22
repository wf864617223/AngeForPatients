package com.hb.rimi.angel.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.StrMd5;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgetStwoActivity extends AppCompatActivity {
    @Bind(R.id.fsta_toolbar)
    Toolbar fsta_toolbar;
    @Bind(R.id.fsta_et_psd)
    EditText fstaEtPsd;
    @Bind(R.id.fsta_et_spsd)
    EditText fstaEtSpsd;
    private String phone;
    private Context mContext;
    private PrgDialog prgDialog;
    private String md5Str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_stwo);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    private void initIntent() {
        mContext = ForgetStwoActivity.this;
        phone = getIntent().getExtras().getString("phone");
    }

    private void initData() {
        //此处设置标题栏，否则在其它位置无效
        fsta_toolbar.setTitle("");
        setSupportActionBar(fsta_toolbar);
        fsta_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        fsta_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        fsta_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fsta_right, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_select_label) {

            String pwd = fstaEtPsd.getText().toString().trim();
            String sPwd = fstaEtSpsd.getText().toString().trim();

            if (StringUtil.isBlank(pwd) || StringUtil.isBlank(sPwd)) {
                T.ShowToast(mContext, "密码不能为空");
            } else {
                if (!pwd.equals(sPwd)) {
                    T.ShowToast(mContext, "两次输入的密码不一致");
                } else {
                    HashMap<String, String> params3 = new HashMap<String, String>();
                    StrMd5 strMd5 = new StrMd5(sPwd);
                    try {
                        md5Str = strMd5.toMd5();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    params3.put("telephone", phone);
                    params3.put("password", md5Str);
                    prgDialog = new PrgDialog(mContext);
                    //修改号码
                    HttpUtil.doHttp(HttpContanst.CHANGE_PWD, params3, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
//                            JSONObject jsonObject = new JSONObject();
                                ResBean resBean= GsonTools.getBean(result,ResBean.class);
//                                JSONObject jsonObject1 = jsonObject.getJSONObject(result);
//                                int status = jsonObject1.getInt("status");
                                int status =resBean.getStatus();
//                                String message = jsonObject1.getString("Message");
                                if (status == 0 ) {
                                    T.ShowToast(mContext, "找回密码成功");
                                    Intent logoutIntent = new Intent(mContext,
                                            LoginActivity.class);
                                    logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(logoutIntent);
                                    finish();
                                } else {
                                    T.ShowToast(mContext, "找回密码失败");
                                    prgDialog.closeDialog();
                                }
                                //prgDialog.closeDialog();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                            prgDialog.closeDialog();
                        }
                    });
                }
            }
        }
        return true;
    }
}
