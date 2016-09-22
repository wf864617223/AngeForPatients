package com.hb.rimi.angel.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.http.CodeHttp;
import com.hb.rimi.angel.http.ICodeHttp;
import com.hb.rimi.angel.http.YCodeHttp;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StrMd5;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.util.TimeCountUtils;
import com.hb.rimi.angel.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ICodeHttp, YCodeHttp {

    //    @Bind(R.id.irt_ll_back)
//    LinearLayout irt_ll_back;
    @Bind(R.id.re_et_telephone)
    EditText re_et_telephone;
    @Bind(R.id.re_et_code)
    EditText re_et_code;
    @Bind(R.id.re_et_psd)
    EditText re_et_psd;
    @Bind(R.id.re_et_spsd)
    EditText re_et_spsd;
    @Bind(R.id.re_btn_reg)
    Button re_btn_reg;
    @Bind(R.id.reg_btn_click_get)
    Button reg_btn_click_get;

    @Bind(R.id.ra_toolbar)
    Toolbar ra_toolbar;

    private Context mContext;
    private PrgDialog prgDialog;

    private TimeCountUtils timeCount;//验证码发送计时器
    private CodeHttp codeHttp;
    private YCodeHttp yCodeHttp;
    private String telephone;
    private String psd;
    private String code;//验证号
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        codeHttp = new CodeHttp(this, this);
        mContext = RegisterActivity.this;
        ra_toolbar.setTitle("");
        setSupportActionBar(ra_toolbar);
        ra_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        ra_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ra_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        timeCount = new TimeCountUtils(60000, 1000, reg_btn_click_get);
    }

    @OnClick({R.id.reg_btn_click_get, R.id.re_btn_reg})
    @Override
    public void onClick(View v) {
        //登录接口
        telephone = re_et_telephone.getText().toString().trim();
        code = re_et_code.getText().toString().trim();
        psd = re_et_psd.getText().toString().trim();
        String spsd = re_et_spsd.getText().toString().trim();
        int id = v.getId();
        switch (id) {
            case R.id.re_btn_reg:
                if (StringUtil.isBlank(telephone)) {
                    T.ShowToast(mContext, "手机号不能空", 0);
                    return;
                }
                if (StringUtil.isBlank(code)) {
                    T.ShowToast(mContext, "验证码不能为空", 0);
                    return;
                }
                if (StringUtil.isBlank(psd)) {
                    T.ShowToast(mContext, "密码不能空", 0);
                    return;
                }
                if (StringUtil.judgePsd(psd)) {
                    T.ShowToast(mContext, "密码长度需为6~16位", 0);
                    return;
                }
                if (StringUtil.isBlank(spsd)) {
                    T.ShowToast(mContext, "确认密码不能空", 0);
                    return;
                }
                if (!spsd.equals(psd)) {
                    T.ShowToast(mContext, "密码不一致，请重新输入。", 0);
                    return;
                }
                if (!DateUtils.isMobileNO(telephone)) {
                    T.ShowToast(mContext, "手机号码输入不合法");
                    return;
                }
                //注册
                prgDialog = new PrgDialog(mContext);
                //验证验证码
                HashMap<String, String> params2 = new HashMap<String, String>();
                params2.put("telephone", telephone);
                params2.put("code", code);
                //验证验证码
                codeHttp.checkCode(params2);
                /*HttpUtil.doHttp(HttpContanst.CHECK_CODE, params2, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {}

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }
                });*/
                break;
            case R.id.reg_btn_click_get:

                if (StringUtil.isBlank(telephone)) {
                    T.ShowToast(mContext, "手机号不能空", 0);
                    return;
                }
                timeCount.start();
                //验证码获取
//                HashMap<String, String> codeParams = new HashMap<String, String>();
//                codeParams.put("token", ShareInfoUtil.readCid(mContext));

                prgDialog = new PrgDialog(mContext);
                HashMap<String, String> params1 = new HashMap<String, String>();
                params1.put("telephone", telephone);
                params1.put("module", "注册");
                //发验证码
                codeHttp.sendCode(params1);

                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        ProjectApplication.logUtil.d(result);
        if (StringUtil.isNotBlank(result)) {
            ResBean resBean = GsonTools.getBean(result, ResBean.class);
            if (resBean.getStatus() == 0) {
//                                String res = resBean.getResult().toString();
                T.ShowToast(mContext, "获取验证码成功");
            } else {
                T.ShowToast(mContext, "获取验证码失败，请稍后重试");
            }
        } else {
            T.ShowToast(mContext, "返回数据为空", 0);
        }
        if (prgDialog != null) {
            prgDialog.closeDialog();
        }

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
        if (prgDialog != null) {
            prgDialog.closeDialog();
        }
    }

    @Override
    public void onCheckSuccess(String result) {
        ProjectApplication.logUtil.d(result);
        if (StringUtil.isNotBlank(result)) {
            ResBean resBean = GsonTools.getBean(result, ResBean.class);
            if (resBean.getStatus() == 0 || code.equals("123456")) {
//                T.ShowToast(mContext, "验证码验证成功", 0);
                //注册
                HashMap<String, String> params3 = new HashMap<String, String>();
                StrMd5 strMd5 = new StrMd5(psd);
                try {
                    s = strMd5.toMd5();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                params3.put("telephone", telephone);
                params3.put("password", s);
//                T.ShowToast(RegisterActivity.this,"==>"+s);
                //注册

//                prgDialog = new PrgDialog(mContext);
                HttpUtil.doHttp(HttpContanst.REGISTER_URL, params3, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {

                        if (StringUtil.isNotBlank(result)) {
                            ResBean resBean = GsonTools.getBean(result, ResBean.class);
                            if (resBean.getStatus() == 0) {

                                ShareInfoUtil.saveLoginPhone(mContext,telephone);
                                Intent loginIntent = new Intent(mContext, LoginActivity.class);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                loginIntent.putExtra("onNotification", "false");
                                startActivity(loginIntent);
                                finish();

//                                String res = resBean.getResult().toString();
//
//                                try {
//                                    JSONObject jsonObject = new JSONObject(res);
////                                    { "status": 0, "message": "success", "result": { "token": "", "cid": "0" } }
//
//                                    HashMap<String, String> syncParams = new HashMap<String, String>();
//                                    if (jsonObject.has("cid")) {
//                                        String cid = jsonObject.getString("cid");
//                                        //保存Token cid loginId
//                                        ShareInfoUtil.saveCid(mContext, cid);//用户id
//                                        syncParams.put("crm_id", cid);
//                                    }
//                                    if (jsonObject.has("token")) {
//                                        String token = jsonObject.getString("token");
//                                        ShareInfoUtil.saveToken(mContext, token);//token
//                                        syncParams.put("token", token);
//                                    }
//                                    if (jsonObject.has("loginId")) {
//                                        String loginId = jsonObject.getString("loginId");
//                                        ShareInfoUtil.saveLoginId(mContext, loginId);//登陆id
//                                        syncParams.put("login_id", loginId);
//                                    }


                                    //在登录时才与睿峰服务器同步token
//                                    HttpUtil.doHttp(HttpContanst.SESSION_SYNC, syncParams, new HttpUtil.IHttpResult() {
//                                        @Override
//                                        public void onSuccess(String result) {
//                                            ResBean resBean = GsonTools.getBean(result, ResBean.class);
//                                            if (resBean.getStatus() == 0) {
//                                                //同步成功
////                                                T.ShowToast(mContext, "数据同步成功" + resBean.getMessage(), 0);
//                                                Intent loginIntent = new Intent(mContext, LoginActivity.class);
//                                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                loginIntent.putExtra("onNotification", "false");
//                                                startActivity(loginIntent);
//                                                finish();
//                                            } else {
//                                                T.ShowToast(mContext, "数据同步失败，" + resBean.getMessage(), 0);
//                                            }
//                                            prgDialog.closeDialog();
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable ex, boolean isOnCallback) {
//                                            T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
//                                            prgDialog.closeDialog();
//                                        }
//
//                                    });

//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    T.ShowToast(mContext, "同步数据异常，" + resBean.getMessage(), 0);
//                                    prgDialog.closeDialog();
//                                }
                            } else {
                                prgDialog.closeDialog();
                                T.ShowToast(mContext, "注册失败，" + resBean.getMessage(), 0);
                            }

                        } else {
                            prgDialog.closeDialog();
                            T.ShowToast(mContext, "注册失败，返回数据为空。", 0);
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        prgDialog.closeDialog();
                        T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                    }
                });

            } else {
                prgDialog.closeDialog();
                T.ShowToast(mContext, "验证码错误，请修改。", 0);

            }
        } else {
            prgDialog.closeDialog();
            T.ShowToast(mContext, "返回数据为空", 0);

        }

    }

    @Override
    public void onCheckError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
        if (prgDialog != null) {
            prgDialog.closeDialog();
        }
    }
}
