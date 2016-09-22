package com.hb.rimi.angel.activity.mine;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.C;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ChangeInfo;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.http.CodeHttp;
import com.hb.rimi.angel.http.ICodeHttp;
import com.hb.rimi.angel.http.YCodeHttp;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.IntentManager;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StrMd5;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.util.TimeCountUtils;
import com.hb.rimi.angel.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 密码重置
 */
@ContentView(R.layout.activity_change_pwd)
public class ChangePwdActivity extends AppCompatActivity implements ICodeHttp,YCodeHttp{

    @Bind(R.id.iv_finish)
    ImageView ivFinish;
    @Bind(R.id.btn_getNum)
    Button btnGetNum;
    @Bind(R.id.et_identifyingCode)
    EditText etIdentifyingCode;
    @Bind(R.id.et_oldPwd)
    EditText etOldPwd;
    @Bind(R.id.et_newPwd)
    EditText etNewPwd;
    @Bind(R.id.et_affinePwd)
    EditText etAffinePwd;
    @Bind(R.id.btn_affinePwd)
    Button btnAffinePwd;
    @Bind(R.id.tv_changPwd_phone)
    TextView tvChangPwdPhone;
    //时间计时器
    private TimeCountUtils time;
    //修改密码的手机号码
    private String changePwdPhone;
    //新密码
    private String newPwd;
    //旧密码
    private String oldPwd;
    private CodeHttp codeHttp;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    //btnGetNum.setBackgroundResource(R.drawable.btn_clicked);
                    break;

            }
            return true;
        }
    });
    private PrgDialog prgDialog;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        //Intent intent = this.getIntent();
        //String phone = intent.getStringExtra("phone");
        codeHttp = new CodeHttp(this,this);
        //String phone = extras.getString("phone");
        String phone = ShareInfoUtil.readLoginPhone(ChangePwdActivity.this);
        tvChangPwdPhone.setText(phone);
        time = new TimeCountUtils(60000, 1000, btnGetNum);
        ivFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGetNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnGetNum.setEnabled(false);
                //Message msg = new Message();
                //msg.what = 1;
                //handler.sendMessage(msg);
                changePwdPhone = tvChangPwdPhone.getText().toString();
                if(TextUtils.isEmpty(changePwdPhone)){
                    T.ShowToast(ChangePwdActivity.this,"获取手机号码失败，请重试",0);
                }else{
                    prgDialog = new PrgDialog(ChangePwdActivity.this);
                    HashMap<String,String> parms = new HashMap<String, String>();
                    parms.put("telephone", changePwdPhone);
                    parms.put("module", "找回密码");
                    codeHttp.sendCode(parms);
                    time.start();
                }

            }
        });
        //点击确认修改的事件
        btnAffinePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //btnAffinePwd.setClickable(false);
                //btnAffinePwd.setBackgroundResource(R.drawable.btn_clicked);
                if (TextUtils.isEmpty(etIdentifyingCode.getText()) || TextUtils.isEmpty(etOldPwd.getText()) || TextUtils.isEmpty(etNewPwd.getText()) || TextUtils.isEmpty(etAffinePwd.getText())) {
                    Toast.makeText(ChangePwdActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else if (!etNewPwd.getText().toString().equals(etAffinePwd.getText().toString())) {
                    Toast.makeText(ChangePwdActivity.this, "输入密码不一致", Toast.LENGTH_SHORT).show();
                } else if(etNewPwd.getText().toString().length()<6){
                    T.ShowToast(ChangePwdActivity.this,"修改的密码最少6位，请重新输入");
                }else {
                    //还要判断旧密码是否输入正确
                    String oldPwd = ShareInfoUtil.readLoginPwd(ChangePwdActivity.this);
                    if(oldPwd.equals(etOldPwd.getText().toString())){
                        changePwdPhone = tvChangPwdPhone.getText().toString();
                        HashMap<String,String> parmas = new HashMap<String, String>();
                        parmas.put("telephone",changePwdPhone);
                        parmas.put("code",etIdentifyingCode.getText().toString());
                        codeHttp.checkCode(parmas);
                    }else{
                        T.ShowToast(ChangePwdActivity.this,"旧密码输入错误");
                    }

                }
            }
        });
    }

    @Override
    public void onSuccess(String result) {
        ProjectApplication.logUtil.d(result);
        if (StringUtil.isNotBlank(result)) {
            ResBean resBean = GsonTools.getBean(result, ResBean.class);
            if (resBean.getStatus() == 0) {
//                                String res = resBean.getResult().toString();
                T.ShowToast(ChangePwdActivity.this, "获取验证码成功");
            } else {
                T.ShowToast(ChangePwdActivity.this, "获取验证码失败，请稍后重试");
            }
        } else {
            T.ShowToast(ChangePwdActivity.this, "返回数据为空", 0);
        }
        if(prgDialog !=null){
            prgDialog.closeDialog();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

        T.ShowToast(ChangePwdActivity.this, "请求失败，请稍后重试。", 0);
        if(prgDialog!=null){
            prgDialog.closeDialog();
        }
    }

    @Override
    public void onCheckSuccess(String result) {
        if(!btnGetNum.isClickable()){
            T.ShowToast(ChangePwdActivity.this,"请先获取验证码");
        }else{
            TimeCountUtils timeCountUtils = new TimeCountUtils(5000,1000,btnAffinePwd);
            timeCountUtils.start();
            ProjectApplication.logUtil.d(result);
            if (StringUtil.isNotBlank(result)) {
                ResBean resBean = GsonTools.getBean(result, ResBean.class);
                if (resBean.getStatus() == 0) {
                    //T.ShowToast(ChangePwdActivity.this, "验证码验证成功", 0);
                    changePwdPhone = tvChangPwdPhone.getText().toString();
                    oldPwd = etOldPwd.getText().toString();
                    newPwd = etNewPwd.getText().toString();
                    StrMd5 strMd5 = new StrMd5(newPwd);
                    try {
                        s = strMd5.toMd5();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    prgDialog = new PrgDialog(ChangePwdActivity.this);
                    HashMap<String, String> params3 = new HashMap<String, String>();
                    params3.put("telephone", changePwdPhone);
                    params3.put("password", s);
                    //修改
                    HttpUtil.doHttp(HttpContanst.CHANGE_PWD, params3, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            ChangeInfo changeInfo = GsonTools.getBean(result,ChangeInfo.class);
                            int status = changeInfo.getStatus();
                            String message = changeInfo.getMessage();
                            //JSONObject jsonObject = new JSONObject();
                            //try {
                            /*JSONObject jsonObject1 = jsonObject.getJSONObject(result);
                            int status = jsonObject1.getInt("status");
                            String message = jsonObject1.getString("Message");*/
                            if(status == 0||message.equals("success")){
                                T.ShowToast(ChangePwdActivity.this,"修改成功");
                                etOldPwd.setText("");
                                etAffinePwd.setText("");
                                etNewPwd.setText("");
                                etIdentifyingCode.setText("");
                                finish();
                            }else{
                                T.ShowToast(ChangePwdActivity.this,"修改失败");
                            }
                            prgDialog.closeDialog();
                            //} catch (JSONException e) {
                            //e.printStackTrace();
                            // }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(ChangePwdActivity.this, "请求失败，请稍后重试。", 0);
                            prgDialog.closeDialog();
                        }
                    });

                } else {
                    T.ShowToast(ChangePwdActivity.this, "验证码错误，请修改。", 0);
                }
            } else {
                T.ShowToast(ChangePwdActivity.this, "返回数据为空", 0);
            }
            prgDialog.closeDialog();
        }

    }

    @Override
    public void onCheckError(Throwable ex, boolean isOnCallback) {

    }
}
