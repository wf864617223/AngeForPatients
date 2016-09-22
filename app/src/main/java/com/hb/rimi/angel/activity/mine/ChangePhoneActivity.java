package com.hb.rimi.angel.activity.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.platform.comapi.map.C;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ChangeInfo;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.http.CodeHttp;
import com.hb.rimi.angel.http.ICodeHttp;
import com.hb.rimi.angel.http.YCodeHttp;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.util.TimeCountUtils;
import com.hb.rimi.angel.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 修改手机号码的界面
 */
@ContentView(R.layout.activity_change_phone)
public class ChangePhoneActivity extends AppCompatActivity implements ICodeHttp,YCodeHttp{

    @Bind(R.id.iv_chanPhone_finish)
    ImageView ivChanPhoneFinish;
    @Bind(R.id.btn_getidentifying)
    Button btnGetidentifying;
    @Bind(R.id.btn_chanPhone)
    Button btnChanPhone;
    @Bind(R.id.et_newPhoneNum)
    EditText etNewPhoneNum;
    @Bind(R.id.et_getidentifyCode)
    EditText etGetidentifyCode;
    @Bind(R.id.et_userPwd)
    EditText etUserPwd;
    @Bind(R.id.change_phone_toolbar)
    Toolbar changePhoneToolbar;
    private TimeCountUtils time;
    private CodeHttp codeHttp;
    private String newPhone;
    private PrgDialog prgDialog;
    private String userPwd;
    private String yzCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        codeHttp = new CodeHttp(this,this);
        initView();
    }

    private void initView() {
        ivChanPhoneFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnGetidentifying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPhone = etNewPhoneNum.getText().toString();
                if(TextUtils.isEmpty(newPhone)){
                    T.ShowToast(ChangePhoneActivity.this,"您输入的号码不能为空",0);
                }else{
                    time = new TimeCountUtils(60000, 1000, btnGetidentifying);
                    prgDialog = new PrgDialog(ChangePhoneActivity.this);
                    HashMap<String,String> parms = new HashMap<String, String>();
                    parms.put("telephone", newPhone);
                    parms.put("module", "找回密码");
                    codeHttp.sendCode(parms);
                    time.start();
                }

            }
        });
        btnChanPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    newPhone = etNewPhoneNum.getText().toString();
                    userPwd = etUserPwd.getText().toString();
                    if(TextUtils.isEmpty(newPhone)||TextUtils.isEmpty(userPwd)||TextUtils.isEmpty(etGetidentifyCode.getText().toString())){
                        T.ShowToast(ChangePhoneActivity.this,"输入不能为空",0);
                    }else if(!DateUtils.isMobileNO(newPhone)){
                        T.ShowToast(ChangePhoneActivity.this,"手机号码输入有误",0);
                        etNewPhoneNum.setText("");
                    }else{
                        if(!btnGetidentifying.isEnabled()){
                            HashMap<String,String> parmas = new HashMap<String, String>();
                            parmas.put("telephone",newPhone);
                            parmas.put("code",etGetidentifyCode.getText().toString());
                            codeHttp.checkCode(parmas);
                        }else{
                            T.ShowToast(ChangePhoneActivity.this,"请先获取验证码");
                        }


                    }

                }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSuccess(String result) {

        ProjectApplication.logUtil.d(result);
        if (StringUtil.isNotBlank(result)) {
            ResBean resBean = GsonTools.getBean(result, ResBean.class);
            if (resBean.getStatus() == 0) {
//                                String res = resBean.getResult().toString();
                T.ShowToast(ChangePhoneActivity.this, "获取验证码成功");
            } else {
                T.ShowToast(ChangePhoneActivity.this, "获取验证码失败，请稍后重试");
            }
        } else {
            T.ShowToast(ChangePhoneActivity.this, "返回数据为空", 0);
        }
        if(prgDialog !=null){
            prgDialog.closeDialog();
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

        T.ShowToast(ChangePhoneActivity.this, "请求失败，请稍后重试。", 0);
        if(prgDialog!=null){
            prgDialog.closeDialog();
        }
    }

    @Override
    public void onCheckSuccess(String result) {
            //btnChanPhone.setClickable(false);

            TimeCountUtils timeCountUtils = new TimeCountUtils(5000,1000,btnChanPhone);
            timeCountUtils.start();
            //btnChanPhone.setBackgroundResource(R.drawable.btn_clicked);

            if (StringUtil.isNotBlank(result)) {
                int status = 0;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    status = jsonObject.optInt("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //ResBean resBean = GsonTools.getBean(result, ResBean.class);
                if (status == 0) {
                    //T.ShowToast(ChangePhoneActivity.this, "验证码验证成功", 0);
                    newPhone = etNewPhoneNum.getText().toString();
                    //userPwd = etUserPwd.getText().toString();
                    UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(ChangePhoneActivity.this);
                    String mobile = ShareInfoUtil.readLoginPhone(ChangePhoneActivity.this);
                    HashMap<String, String> params3 = new HashMap<String, String>();
                    params3.put("oldTelephone", mobile);
                    params3.put("newTelephone", newPhone);
                    /*if(!DateUtils.isMobileNO(newPhone)){
                        T.ShowToast(ChangePhoneActivity.this,"手机号输入不合法");
                        return;
                    }*/
                    //修改号码
                    HttpUtil.doHttp(HttpContanst.CHANGE_PHONE, params3, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            ChangeInfo changeInfo = GsonTools.getBean(result, ChangeInfo.class);
                            int status = changeInfo.getStatus();
                            String message = changeInfo.getMessage();
                            //try {

                            if(status == 0||message.equals("Success")){
                                T.ShowToast(ChangePhoneActivity.this,"修改成功");
                                finish();
                            }else{
                                T.ShowToast(ChangePhoneActivity.this,"修改失败");
                                btnChanPhone.setClickable(true);
                            }
                            // } catch (JSONException e) {
                            //  e.printStackTrace();
                            // }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            T.ShowToast(ChangePhoneActivity.this, "请求失败，请稍后重试。", 0);
                            prgDialog.closeDialog();
                        }
                    });

                } else {
                    T.ShowToast(ChangePhoneActivity.this, "验证码错误，请修改。", 0);
                }
            } else {
                T.ShowToast(ChangePhoneActivity.this, "返回数据为空", 0);
            }
            prgDialog.closeDialog();


    }

    @Override
    public void onCheckError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        T.ShowToast(ChangePhoneActivity.this, "请求失败，请稍后重试。", 0);
        prgDialog.closeDialog();
    }
}
