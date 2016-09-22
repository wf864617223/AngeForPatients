package com.hb.rimi.angel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.hb.generalupdate.InitUpdateInterface;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.mine.LoginActivity;
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
import com.hb.update.UpdateManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity implements InitUpdateInterface {
    private String isNotifi = "";
    private String isLogin = "";
    private Context mContext;
    @SuppressLint("HandlerLeak")
    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ShareInfoUtil.saveIsNewVer(mContext, false);
                    // To do something.
//                    T.ShowToast(SplashActivity.this, "暂无更新");
                    Log.d("GeneralUpdateLib", "There's no new version here.");
                    break;
                case 1:
                    // Find new version.
                    ShareInfoUtil.saveIsNewVer(mContext, true);
                    break;
                default:
                    break;
            }
        }
    };
    private PrgDialog prgDialog;
    private String md5Str;
    private String telephone = "";
    /**
     * 环信注册反馈
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
                                        syncAndhxLogin();
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
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.layout_splash);
        getSupportActionBar().hide();

        mContext = SplashActivity.this;

        ShareInfoUtil.saveParams(mContext, "appstate", "1");
        //每次启动时冲掉已缓存的会诊卡信息，防止后面打开时调用到
        // 检查升级
        checkUpdate();
        initIntent();

    }

    private void initIntent() {
        final Intent intent = new Intent(mContext, LoginActivity.class);

        try {
            isLogin = ShareInfoUtil.readParams(mContext, "isLogin");
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                isNotifi = bundle.getString("onNotification");
            }
            if ("true".equals(isNotifi)) {
                intent.putExtra("onNotification", "true");
            } else if ("truetrue".equals(isNotifi)) {
                intent.putExtra("onNotification", "truetrue");
            }

            if ("true".equals(isLogin)) {
                telephone = ShareInfoUtil.readLoginPhone(mContext);
                String psd = ShareInfoUtil.readLoginPwd(mContext);
                //自动登录
                login(telephone, psd);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }

    /**
     * 未自动登录成功 跳转到登录页面
     */
    public void notAutoLogin() {
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
            notAutoLogin();
            return;
        }
        if (StringUtil.isBlank(password)) {
            T.ShowToast(mContext, "密码不能空", 0);
            notAutoLogin();
            return;
        }
        if (StringUtil.judgePsd(password)) {
            T.ShowToast(mContext, "密码错误，请重新输入。", 0);
            notAutoLogin();
            return;
        }
        HashMap<String, String> params = new HashMap<String, String>();
        StrMd5 strMd5 = new StrMd5(password);
        try {
            md5Str = strMd5.toMd5();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //T.ShowToast(Login Activity.this,"==>"+s);
        params.put("telephone", telephone);
        params.put("password", md5Str);
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
                            notAutoLogin();
                            prgDialog.closeDialog();
                        }

                    } else {
                        ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                        notAutoLogin();
                        T.ShowToast(mContext, "登陆失败" + resBean.getMessage(), 0);
                        prgDialog.closeDialog();
                    }
                } else {
                    prgDialog.closeDialog();
//                    ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                    T.ShowToast(mContext, "返回数据为空", 0);
                    notAutoLogin();
                    prgDialog.closeDialog();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                prgDialog.closeDialog();
//                ShareInfoUtil.saveParams(mContext, "isLogin", "false");
                notAutoLogin();
            }
        });
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
                                            prgDialog.closeDialog();
                                        } else {
                                            //创建环信账号
                                            //T.ShowToast(mContext,"开始创建环信账号");
                                            createHxAccount(telephone);
                                            ProjectApplication.intentManager.toMainActivity(null);
                                            finish();
                                            prgDialog.closeDialog();
                                        }
                                    } else {
                                        T.ShowToast(mContext, "请求数据错误，" + jsonObject.getString("message"));
                                        notAutoLogin();
                                        prgDialog.closeDialog();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    T.ShowToast(mContext, "查询环信账号数据异常，请稍后重试。", 0);
                                    notAutoLogin();
                                    prgDialog.closeDialog();
                                }

                            } else {
                                //网络错误
                                T.ShowToast(mContext, "返回数据为空", 0);
                                notAutoLogin();
                                prgDialog.closeDialog();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(mContext, "请求失败，请稍后重试。" + ex.getMessage(), 0);
                            notAutoLogin();
                            prgDialog.closeDialog();
                        }
                    });

                } else {
                    T.ShowToast(mContext, "登陆失败" + resBean.getMessage(), 0);
                    notAutoLogin();
                    prgDialog.closeDialog();
                }
//                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。" + ex.getMessage(), 0);
//                ProjectApplication.intentManager.toMainActivity(null);
//                finish();
                notAutoLogin();
                prgDialog.closeDialog();
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
                    //注册失败
                    regHandler.sendEmptyMessage(1);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void checkUpdate() {
        ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cwjManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            CheckVerRunnable chackRunnable = new CheckVerRunnable();
            Thread checkThread = new Thread(chackRunnable);
            checkThread.start();
        } else {
            T.ShowToast(mContext, "网络连接失败，请检查。", 1);
            Log.d("GeneralUpdateLib",
                    "Network connection failed, please check the network.");
        }
    }

    class CheckVerRunnable implements Runnable {
        @Override
        public void run() {
            // replace your .json file url.
            boolean isUpdate = UpdateManager
                    .getUpdateInfo(
                            SplashActivity.this,
                            HttpContanst.UPDATE_URL,
                            true);
            if (isUpdate) {
                updateHandler.sendEmptyMessage(1);
            } else {
                updateHandler.sendEmptyMessage(0);
            }
        }
    }
}
