package com.hb.rimi.angel.activity.pay;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.util.T;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 支付管理
 */

public class PayActivity extends AppCompatActivity {
    @Bind(R.id.pa_toolbar)
    Toolbar paToolbar;
    private AlipayFragment alipayFragment;
    private WxFragment wxFragment;
    private int payType;//1为微信 2为支付宝
    private Context mContext;
    private String alipayContruct;//支付宝参数
    private String result;//微信参数

    public static String keyResult = "result";
    public static String projectType = "projectType";//0,1,2,3 0代表预约挂号  1检查项目 2门诊缴费 3商城
    public static String projectTypeValue ;//0,1,2,3 0代表预约挂号  1检查项目 2门诊缴费 3商城
    public static boolean isAlipayPay = false;
    public static boolean isWxPay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    private void initIntent() {

        try {
            payType = getIntent().getExtras().getInt("payType", 0);

            //得到传递的参数
            if (payType == Contanst.PAYTYPE_WX) {
                result = getIntent().getExtras().getString(PayActivity.keyResult);
            } else if (payType == Contanst.PAYTYPE_ALPAY) {
                alipayContruct = getIntent().getExtras().getString("alipayContruct");
            }
            projectTypeValue = getIntent().getExtras().getString(projectType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initData() {
        mContext = PayActivity.this;

        paToolbar.setTitle("");
        setSupportActionBar(paToolbar);
        paToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        paToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        paToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //实例支付宝
        alipayFragment = new AlipayFragment();
        wxFragment = new WxFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.ip_fl_pay, wxFragment);
        fragmentTransaction.add(R.id.ip_fl_pay, alipayFragment);
        fragmentTransaction.commit();

        if (payType == Contanst.PAYTYPE_WX) {
            showFragment(Contanst.PAYTYPE_WX);
        } else if (payType == Contanst.PAYTYPE_ALPAY) {
            showFragment(Contanst.PAYTYPE_ALPAY);
        } else {
            T.ShowToast(mContext, "错误的跳转");
        }
    }


    //显示与隐藏支付按钮
    private void showFragment(int which) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(alipayFragment);
        fragmentTransaction.hide(wxFragment);
        switch (which) {
            case 1:
                Bundle wxbundle = new Bundle();
                wxbundle.putString(PayActivity.keyResult, result);
                wxbundle.putString(PayActivity.projectType, projectTypeValue);
                wxbundle.putInt("payType", payType);
                wxFragment.setArguments(wxbundle);
                fragmentTransaction.show(wxFragment);
                break;
            case 2:
                Bundle albundle = new Bundle();
                albundle.putString("alipayContruct", alipayContruct);
                albundle.putInt("payType", payType);
                albundle.putString(PayActivity.projectType, projectTypeValue);
                alipayFragment.setArguments(albundle);

                fragmentTransaction.show(alipayFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
