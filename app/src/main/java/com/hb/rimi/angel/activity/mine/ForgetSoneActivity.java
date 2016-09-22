package com.hb.rimi.angel.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResBean;
import com.hb.rimi.angel.http.CodeHttp;
import com.hb.rimi.angel.http.ICodeHttp;
import com.hb.rimi.angel.http.YCodeHttp;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetSoneActivity extends AppCompatActivity implements View.OnClickListener, ICodeHttp, YCodeHttp {
    @Bind(R.id.fsoa_toolbar)
    Toolbar fsoa_toolbar;
    @Bind(R.id.fsa_et_phone)
    EditText fsaEtPhone;
    @Bind(R.id.fsa_et_code)
    EditText fsaEtCode;
    @Bind(R.id.fsa_btn_get)
    Button fsaBtnGet;
    private Context mContext;

    private CodeHttp codeHttp;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_sone);
        ButterKnife.bind(this);
        mContext = ForgetSoneActivity.this;
        initData();
    }

    private void initData() {
        //此处设置标题栏，否则在其它位置无效
        fsoa_toolbar.setTitle("");
        setSupportActionBar(fsoa_toolbar);
        fsoa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        fsoa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        fsoa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        codeHttp = new CodeHttp(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fsoa_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_select_label) {

            String phone = fsaEtPhone.getText().toString().trim();
            String code = fsaEtCode.getText().toString().trim();
            if (phone.length() != 11) {
                T.ShowToast(mContext, "手机号错误");

            } else {
                if (StringUtil.isBlank(code)) {
                    T.ShowToast(mContext, "请输入验证码");
                } else {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("telephone", phone);
                    params.put("code", code);
                    codeHttp.checkCode(params);
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.fsa_btn_get})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fsa_btn_get:
                String phone = fsaEtPhone.getText().toString().trim();
                if (phone.length() != 11) {
                    T.ShowToast(mContext, "手机号错误!");
                    return;
                }
                if(DateUtils.isMobileNO(phone)){
                    T.ShowToast(mContext, "手机号错误!");
                    return;
                }
                HashMap<String, String> parms = new HashMap<String, String>();
                parms.put("telephone", phone);
                parms.put("module", "找回密码");
                prgDialog = new PrgDialog(mContext);
                codeHttp.sendCode(parms);
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
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
        if (StringUtil.isNotBlank(result)) {
            ResBean resBean = GsonTools.getBean(result, ResBean.class);
            if (resBean.getStatus() == 0) {
                Bundle bundle = new Bundle();
                String phone = fsaEtPhone.getText().toString().trim();
                bundle.putString("phone", phone);
                String res = resBean.getResult().toString();
                try {
                    JSONObject json = new JSONObject(res);
                    if (json.has("valid") && json.getBoolean("valid") == true) {
                        ProjectApplication.intentManager.toForgetStwoActivity(bundle);
                    }else{
                        T.ShowToast(mContext, "验证码错误");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                T.ShowToast(mContext, "验证码错误");
            }
        } else {
            T.ShowToast(mContext, "请求失败，请稍后重试");
        }
    }

    @Override
    public void onCheckError(Throwable ex, boolean isOnCallback) {

    }
}
