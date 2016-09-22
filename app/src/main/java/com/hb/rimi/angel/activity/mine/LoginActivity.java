package com.hb.rimi.angel.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.MedicalVoucherActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StrMd5;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.la_btn_login)
    Button la_btn_login;
    @Bind(R.id.la_tv_register)
    TextView la_tv_register;
    @Bind(R.id.la_tv_forget)
    TextView la_tv_forget;
    @Bind(R.id.la_tv_cancel)
    TextView la_tv_cancel;

    @Bind(R.id.la_et_telephone)
    EditText la_et_telephone;
    @Bind(R.id.la_et_password)
    EditText la_et_password;
    String telephone = "";
    private Context mContext;
    private PrgDialog prgDialog;
    /**
     * 注册反馈
     */
    protected Handler regHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    HashMap<String, String> params = new HashMap<>();
                    params.put("token", ShareInfoUtil.readToken(mContext));
                    params.put("push_token", ShareInfoUtil.readChannelId(mContext));
                    params.put("easemob_token", ShareInfoUtil.readEasemobToken(mContext));
                    HttpUtil.doHttp(HttpContanst.CREATE_HY_USERNAME, params, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            if (result != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getInt("status") == 0) {
//                                        T.ShowToast(mContext, "环信token同步成功");
                                        //开始登录
//                                        syncAndhxLogin();
                                    } else {
                                        T.ShowToast(mContext, "环信token同步失败");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                T.ShowToast(mContext, "返回数据为空");
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "请求错误，请稍后重试" + ex.getMessage());
                        }
                    });
                    break;

                case 1:
                    T.ShowToast(mContext, "环信账号创建失败，请稍后重试");
                    break;
                case 2:
                    //环信账号已存在
                    //同步环信账号，否则会出现修改手机号重新注册时导致无法登录的问题
                    HashMap<String, String> paramsExist = new HashMap<>();
                    paramsExist.put("token", ShareInfoUtil.readToken(mContext));
                    paramsExist.put("push_token", ShareInfoUtil.readChannelId(mContext));
                    //这个时候直接使用该登录手机号作为环信账号
                    ShareInfoUtil.saveEasemobToken(mContext, telephone);
                    paramsExist.put("easemob_token", ShareInfoUtil.readEasemobToken(mContext));
                    HttpUtil.doHttp(HttpContanst.CREATE_HY_USERNAME, paramsExist, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            if (result != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getInt("status") == 0) {
//                                        T.ShowToast(mContext, "环信token同步成功");
                                        //开始登录
//                                        syncAndhxLogin();
                                    } else {
                                        T.ShowToast(mContext, "环信token同步失败");
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                T.ShowToast(mContext, "返回数据为空");
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "请求错误，请稍后重试" + ex.getMessage());
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };
    private String password;
    private String md5Str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initIntent();
    }

    private void initIntent() {
        //判断是否是消息栏点过来的
        try {
            if ("true".equals(getIntent().getExtras().getString("onNotification"))) {
                Intent msgIntent = new Intent(this, MyMessageActivity.class);
                msgIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(msgIntent);
            } else if ("truetrue".equals(getIntent().getExtras().getString("onNotification"))) {
                Intent msgIntent = new Intent(this, MedicalVoucherActivity.class);
                msgIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(msgIntent);
            } else {
                initData();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            initData();
        }
    }

    private void initData() {
        mContext = LoginActivity.this;
        la_et_telephone.setText(ShareInfoUtil.readLoginPhone(mContext));
        la_et_password.setText(ShareInfoUtil.readLoginPwd(mContext));

        String isLogin = ShareInfoUtil.readParams(mContext, "isLogin");
        if ("true".equals(isLogin)) {
            String tel = ShareInfoUtil.readLoginPhone(mContext);
            String psd = ShareInfoUtil.readLoginPwd(mContext);
            //自动登录
            login(tel, psd);
        }
    }

    @OnClick({R.id.la_btn_login, R.id.la_tv_register, R.id.la_tv_forget, R.id.la_tv_cancel})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.la_btn_login:
                //登录接口
                telephone = la_et_telephone.getText().toString().trim();
                password = la_et_password.getText().toString().trim();
                login(telephone, password);

                break;
            case R.id.la_tv_register:
                ProjectApplication.intentManager.toRegisterActivity(null);
                break;
            case R.id.la_tv_forget:
                ProjectApplication.intentManager.toForgetSoneActivity(null);
                break;
            case R.id.la_tv_cancel:
                finish();
                break;
        }

    }

    public void toLoginAct() {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        loginIntent.putExtra("onNotification", "false");
        startActivity(loginIntent);
        finish();
    }

    /**
     * 登录
     */
    public void login(final String telephone, final String password) {


        if (StringUtil.isBlank(telephone)) {
            T.ShowToast(mContext, "手机号不能空", 0);
            return;
        }
        if (StringUtil.isBlank(password)) {
            T.ShowToast(mContext, "密码不能空", 0);
            return;
        }
        if (StringUtil.judgePsd(password)) {
            T.ShowToast(mContext, "密码错误，请重新输入。", 0);
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        StrMd5 strMd5 = new StrMd5(password);
        try {
            md5Str = strMd5.toMd5();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //T.ShowToast(Login Activity.this,"==>"+md5Str);
        params.put("telephone", telephone);
        params.put("password", md5Str);
        System.out.println("密码==》"+ md5Str);
        prgDialog = new PrgDialog(mContext);
//                warnDialog= new WarningDialog(mContext);
        HttpUtil.doHttp(HttpContanst.LOGIN_URL, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResBean resBean = GsonTools.getBean(result, ResBean.class);
                    if (resBean.getStatus() == 0) {
                        final String res = resBean.getResult().toString();

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            String cid = jsonObject.getString("cid");
                            String token = jsonObject.getString("token");
                            String loginId = jsonObject.getString("loginId");

                            //保存Token cid loginId
                            //保存登录电话
                            ShareInfoUtil.saveLoginPhone(mContext, telephone);
                            ShareInfoUtil.saveLoginPwd(mContext, password);//登录密码
                            ShareInfoUtil.saveCid(mContext, cid);//用户id
                            ShareInfoUtil.saveToken(mContext, token);//token
                            ShareInfoUtil.saveLoginId(mContext, loginId);//登陆id
                            //自动登录标记
                            ShareInfoUtil.saveParams(mContext, "isLogin", "true");
                            //同步后登录环信 并跳转
                            syncAndhxLogin();
                            //finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                        }

                    } else {
                        ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                        T.ShowToast(mContext, "登陆失败" + resBean.getMessage(), 0);
                    }
                } else {
//                    ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                    T.ShowToast(mContext, "返回数据为空", 0);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                prgDialog.closeDialog();
//                ShareInfoUtil.saveParams(mContext, "isLogin", "false");
            }
        });
    }

    /**
     * 创建环信账号
     */
    public void createHxAccount(final String hxUserName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(hxUserName, Contanst.hxPsd);
                    ShareInfoUtil.saveEasemobToken(mContext, hxUserName);
                    regHandler.sendEmptyMessage(0);
                    //注册成功
                } catch (HyphenateException e) {
                    System.out.println("ecode==============>"+e.getErrorCode());
                    if(e.getErrorCode()== EMAError.USER_ALREADY_EXIST){
                        regHandler.sendEmptyMessage(2); //账户已存在
                    }else{
                        regHandler.sendEmptyMessage(1); //注册失败
                    }
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 登录同步并登录环信成功后跳转到首页
     */
    public void syncAndhxLogin() {

        HashMap<String, String> syncParams = new HashMap<String, String>();
        syncParams.put("token", ShareInfoUtil.readToken(mContext));
        syncParams.put("crm_id", ShareInfoUtil.readCid(mContext));
        syncParams.put("login_id", ShareInfoUtil.readLoginId(mContext));
        //与睿峰服务器同步token
        HttpUtil.doHttp(HttpContanst.SESSION_SYNC, syncParams, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                ResBean resBean = GsonTools.getBean(result, ResBean.class);
                if (resBean.getStatus() == 0) {
                    //查询环信账号是否存在
                    HttpUtil.doHttp(HttpContanst.SELECT_HY_USERNAME + ShareInfoUtil.readToken(mContext), null, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            if (result != null) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int status = jsonObject.getInt("status");
                                    if (status == 0) {
                                        String res = jsonObject.getString("result");
                                        JSONObject jsonObject1 = new JSONObject(res);

                                        if ((jsonObject1.has("easemob_token") && StringUtil.isNotBlank(jsonObject1.getString("easemob_token")))) {

                                            String easemobToken = jsonObject1.getString("easemob_token");
                                            //保存环信账号
                                            ShareInfoUtil.saveEasemobToken(mContext, easemobToken);
                                            //同步成功
                                            //登录环信UI   环信注册同一账号：手机号隐藏中间四位,密码：Angel888
                                            // 测试账号  258369 123456   进行登陆  非群主qwert  123456  | phone 与Angel888
                                            //T.ShowToast(mContext,"后台存在环信账号，直接调用====>"+jsonObject1.getString("easemob_token"));


                                            ProjectApplication.intentManager.toMainActivity(null);
                                            finish();
                                        } else {
                                            //创建环信账号
                                            //T.ShowToast(mContext,"开始创建环信账号");
                                            createHxAccount(telephone);
                                            ProjectApplication.intentManager.toMainActivity(null);
                                            finish();
                                        }
                                    } else {
                                        T.ShowToast(mContext, "请求数据错误" + jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    T.ShowToast(mContext, "查询环信账号数据异常，请稍后重试。", 0);
                                }

                            } else {
                                //网络错误
                                T.ShowToast(mContext, "返回数据为空", 0);
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "请求失败，请稍后重试。" + ex.getMessage(), 0);
                            prgDialog.closeDialog();
                        }
                    });

                } else {
                    T.ShowToast(mContext, "登陆失败" + resBean.getMessage(), 0);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。" + ex.getMessage(), 0);
                prgDialog.closeDialog();
                ProjectApplication.intentManager.toMainActivity(null);
                finish();
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            ShareInfoUtil.saveParams(this, "appstate", "0");
            return true;
        }
        return false;
    }
}
