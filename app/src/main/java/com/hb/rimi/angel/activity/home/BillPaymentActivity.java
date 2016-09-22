package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.pay.PayActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResPayQuick;
import com.hb.rimi.angel.bean.ResWxpayQuick;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BillPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.billpay_toolbar)
    Toolbar billpay_toolbar;
    @Bind(R.id.ip_tv_pay_amount)
    TextView ipTvPayAmount;
    @Bind(R.id.ip_tv_symbol)
    TextView ipTvSymbol;
    @Bind(R.id.ip_iv_wx)
    ImageView ipIvWx;

    @Bind(R.id.ip_iv_alpay)
    ImageView ipIvAlpay;
    @Bind(R.id.ip_rb_alpay)
    RadioButton ipRbAlpay;
    @Bind(R.id.ip_rb_wx)
    RadioButton ipRbWx;
    @Bind(R.id.ip_btn_pay)
    Button ipBtnPay;

    @Bind(R.id.ip_rl_wx)
    RelativeLayout ipRlWx;
    @Bind(R.id.ip_rl_alipay)
    RelativeLayout ipRlAlipay;

    private Context mContext;
    //参数
    private String data;
    private String amount="1";
    private String price;
    private String name;
    private int bpayType = 1;//默认第一次调用微信

    private PrgDialog prgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_bill_payment2);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    private void initIntent() {

        data = getIntent().getExtras().getString("data");
//        amount = getIntent().getExtras().getString("amount");
        price = getIntent().getExtras().getString("price");
        name = getIntent().getExtras().getString("name");
    }

    private void initData() {
        mContext = BillPaymentActivity.this;
        billpay_toolbar.setTitle("");
        setSupportActionBar(billpay_toolbar);
        billpay_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        billpay_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        billpay_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ipRbWx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ipRbWx.setChecked(true);
                    ipRbAlpay.setChecked(false);
                    bpayType = Contanst.PAYTYPE_WX;
                }
            }
        });

        ipRbAlpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ipRbAlpay.setChecked(true);
                    ipRbWx.setChecked(false);
                    bpayType = Contanst.PAYTYPE_ALPAY;
                }
            }
        });

        ipTvPayAmount.setText(price);
    }

    @OnClick({R.id.ip_rl_wx, R.id.ip_rl_alipay, R.id.ip_btn_pay})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ip_btn_pay:
                HashMap<String, String> params = new HashMap<>();

                UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
                if (resultBean!=null&&StringUtil.isBlank(resultBean.getIC_NO())) {
                    T.ShowToast(mContext, "请添加就诊人信息");
                    return;
                }

                if (StringUtil.isBlank(price)) {
                    T.ShowToast(mContext, "金额错误，稍后重试");
                    return;
                }
                params.put("token", ShareInfoUtil.readToken(mContext));
                params.put("name", name);
                params.put("price", price);
                params.put("amount", amount);
                params.put("data", data);

                if (bpayType == 1) {
                    params.put("way", "wechat");
                } else if (bpayType == 2) {
                    params.put("way", "alipay");
                }
                System.out.println(params.toString());
                prgDialog = new PrgDialog(mContext);
                HttpUtil.doHttp(HttpContanst.CREATE_ORDER_OUTPATIENT, params, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        if (result != null) {
                            System.out.println("服务器返回支付参数==============" + result);
                            if (bpayType == 1) {//微信
                                ResWxpayQuick resWxpayQuick = GsonTools.getBean(result, ResWxpayQuick.class);
                                if (resWxpayQuick.getStatus() == 0) {
//                                    T.ShowToast(mContext, "创建订单成功跳转到微信支付界面。。。");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("payType", Contanst.PAYTYPE_WX);
                                    bundle.putString(PayActivity.keyResult, result);
                                    bundle.putString(PayActivity.projectType, "2");

                                    ProjectApplication.intentManager.toPayActivity(bundle);
                                } else {
                                    T.ShowToast(mContext, resWxpayQuick.getMessage());
                                }
                            } else if (bpayType == 2) {
                                ResPayQuick resPayQuick = GsonTools.getBean(result, ResPayQuick.class);
                                if (resPayQuick.getStatus() == 0) {
//                                    T.ShowToast(mContext, "创建订单成功跳转到支付宝支付界面。。。");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("payType", Contanst.PAYTYPE_ALPAY);
                                    bundle.putString(PayActivity.projectType, "2");
                                    bundle.putString("alipayContruct", resPayQuick.getResult().getAlipayContruct());
                                    ProjectApplication.intentManager.toPayActivity(bundle);
                                } else {
                                    T.ShowToast(mContext, resPayQuick.getMessage());
                                }
                            }
                        } else {
                            T.ShowToast(mContext, "订单创建失败，返回数据为空", 0);
                        }
                        prgDialog.closeDialog();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        T.ShowToast(mContext, "创建订单失败，请重试。");
                        prgDialog.closeDialog();
                    }
                });
                break;
            case R.id.ip_rl_wx:
                ipRbWx.setChecked(true);
                ipRbAlpay.setChecked(false);
                bpayType = Contanst.PAYTYPE_WX;
                break;
            case R.id.ip_rl_alipay:
                ipRbAlpay.setChecked(true);
                ipRbWx.setChecked(false);
                bpayType = Contanst.PAYTYPE_ALPAY;
                break;
        }
    }
}
