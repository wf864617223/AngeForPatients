package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpLoadImg;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.pay.PayActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResDoctorCost;
import com.hb.rimi.angel.bean.ResPayQuick;
import com.hb.rimi.angel.bean.ResWxpayQuick;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.util.VocationalUtil;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约挂号支付
 */
public class AppointmentPayActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.apa_toolbar)
    Toolbar apa_toolbar;
    @Bind(R.id.apa_tv_visiting_time)
    TextView apa_tv_visiting_time;
    @Bind(R.id.ip_rb_alpay)
    RadioButton ip_rb_alpay;
    @Bind(R.id.ip_rb_wx)
    RadioButton ip_rb_wx;
    //    @Bind(R.id.ip_radioGroup)
//    RadioGroup ip_radioGroup;

    @Bind(R.id.apa_tv_position)
    TextView apa_tv_position;
    @Bind(R.id.apa_tv_name)
    TextView apa_tv_name;
    @Bind(R.id.apa_iv_docimg)
    ImageView apa_iv_docimg;
    @Bind(R.id.ip_btn_pay)
    Button ip_btn_pay;
    @Bind(R.id.apa_ll_call)
    LinearLayout apaLlCall;
    @Bind(R.id.ip_tv_pay_amount)
    TextView ipTvPayAmount;
    @Bind(R.id.ip_tv_symbol)
    TextView ipTvSymbol;
    @Bind(R.id.ip_iv_wx)
    ImageView ipIvWx;
    @Bind(R.id.ip_iv_alpay)
    ImageView ipIvAlpay;
    @Bind(R.id.apa_et_note)
    EditText apaEtNote;
    @Bind(R.id.apa_title)
    TextView apa_title;
    @Bind(R.id.lui_tv_name)
    TextView luiTvName;
    @Bind(R.id.lui_tv_sex)
    TextView luiTvSex;
    @Bind(R.id.lui_tv_birth)
    TextView luiTvBirth;
    @Bind(R.id.lui_tv_last_menes)
    TextView luiTvLastMenes;
    @Bind(R.id.lui_ll_last_menes)
    LinearLayout luiLlLastMenes;
    @Bind(R.id.lui_tv_notice)
    TextView luiTvNotice;
    @Bind(R.id.lv_add_userinfo)
    ImageView lvAddUserinfo;

    @Bind(R.id.ip_rl_wx)
    RelativeLayout ipRlWx;
    @Bind(R.id.ip_rl_alipay)
    RelativeLayout ipRlAlipay;

//    @Bind(R.id.imci_ll_card_info)
//    LinearLayout imci_ll_card_info;


    private Context mContext;
    //获取上个页面传递的医生id 名称 图片 职位 日期 周
    private String id;
    private String name;
    private String code;
    private String url;
    private String position;
    private String date;
    private String week;
    private String dptId;//科室ID
    private String dptName;//科室名称

    private int bpayType = 1;//  1微信支付 2支付宝支付

    private String orderName = "医生挂号";// 预约挂号订单名字
    private String orderCount = "1";// 商品数量默认为1
    private String orderData = "";
    private double orderPrice = 0;//订单价格整型
    //    {
//        "department":"科室id",
//            "doctor":"医生id",
//            "date":"日期值"
//    }
    private PrgDialog prgDialog;
    private String ref_regtype = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_appointment_pay);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
       try{

         UserInfo.ResultBean resultBean=  ShareInfoUtil.readResultBean(mContext);
           String d_name = resultBean.getName();
           String d_birthday = resultBean.getBirthday();
           String d_userSex = resultBean.getSex();
           if ("1".equals(d_userSex)) {
               luiTvSex.setText("男");
           } else if ("0".equals(d_userSex)) {
               luiTvSex.setText("女");
           }
           System.out.print("d_name=="+d_name +"d_birthday=="+d_birthday+"d_userSex=="+d_userSex);
           luiTvName.setText(d_name);
           luiTvBirth.setText(d_birthday);


       }catch (Exception ex){
           ex.printStackTrace();
       }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 3:
                if (resultCode == RESULT_OK) {
                    String addSuc="";
                    Bundle bundle=data.getExtras();
                    if(bundle!=null){
                        addSuc= bundle.getString("addSuc");
                    }



                    String d_name = ShareInfoUtil.readParams(mContext, "d_name");
                    String d_birthday = ShareInfoUtil.readParams(mContext, "d_birthday");
                    String d_userSex = ShareInfoUtil.readParams(mContext, "d_userSex");
                    System.out.print("d_name=="+d_name +"d_birthday=="+d_birthday+"d_userSex=="+d_userSex);
                    luiTvName.setText(d_name);
                    luiTvBirth.setText(d_birthday);

                    if ("1".equals(d_userSex)) {
                        luiTvSex.setText("男");
                    } else if ("0".equals(d_userSex)) {
                        luiTvSex.setText("女");
                    }

                    if ("true".equals(addSuc)) {
                        lvAddUserinfo.setVisibility(View.GONE);
                    } else {
                        lvAddUserinfo.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }


    private void initIntent() {
        id = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        code = getIntent().getExtras().getString("code");
        url = getIntent().getExtras().getString("url");
        position = getIntent().getExtras().getString("position");
        dptId = getIntent().getExtras().getString("dptId");
        dptName = getIntent().getExtras().getString("dptName");
        date = getIntent().getExtras().getString("date");
        week = getIntent().getExtras().getString("week");

    }

    private void initData() {
        mContext = AppointmentPayActivity.this;

        apa_toolbar.setTitle("");
        setSupportActionBar(apa_toolbar);
        apa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        apa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        apa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //赋初始值
        apa_tv_name.setText(name);
        apa_tv_position.setText(position);
        HttpLoadImg.loadCircleImg(mContext, url, apa_iv_docimg);
        apa_tv_visiting_time.setText(date + " " + week);
//        if (hasIcNo()) {
//            imci_ll_card_info.setVisibility(View.GONE);
//        } else {
//            imci_ll_card_info.setVisibility(View.VISIBLE);
//        }

        ip_rb_wx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ip_rb_wx.setChecked(true);
                    ip_rb_alpay.setChecked(false);
                    bpayType = 1;

                }
            }
        });

        ip_rb_alpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ip_rb_alpay.setChecked(true);
                    ip_rb_wx.setChecked(false);
                    bpayType = 2;
                }
            }
        });
        prgDialog = new PrgDialog(mContext);
        //获取医生价格
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "doctorInfo");
        params.put("dept_name", dptName);
        luiLlLastMenes.setVisibility(View.GONE);
        if (!VocationalUtil.hasIcNo(mContext)) {
            lvAddUserinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentadd = new Intent(mContext, AddUserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", id);
                    bundle.putString("name", name);
                    bundle.putString("code", code);
                    bundle.putString("url", url);
                    bundle.putString("position", position);
                    bundle.putString("dptId", dptId);
                    bundle.putString("dptName", dptName);
                    bundle.putString("date", date);
                    bundle.putString("week", week);
                    bundle.putString("targetType", "1");
                    intentadd.putExtras(bundle);
                    intentadd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intentadd, 3);

                }
            });
        } else {
            luiTvNotice.setVisibility(View.GONE);
//            lvAddUserinfo.setVisibility(View.GONE);
            UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(AppointmentPayActivity.this);
            luiTvName.setText(resultBean.getName());
            String sex = resultBean.getSex();
            if ("1".equals(sex)) {
                luiTvSex.setText("男");
            } else {
                luiTvSex.setText("女");
            }

            luiTvBirth.setText(resultBean.getBirthday());
        }
        //判断一下
        if(VocationalUtil.hasCid(mContext)){
            lvAddUserinfo.setVisibility(View.GONE);
        }else{
            lvAddUserinfo.setVisibility(View.VISIBLE);
        }
        HttpUtil.doHttp(HttpContanst.DOCTOR_COST, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    ResDoctorCost resDoctorCost = GsonTools.getBean(result, ResDoctorCost.class);
                    if (resDoctorCost.getStatus() == 0) {
                        List<ResDoctorCost.ResultBean> resultBeanList = resDoctorCost.getResult();
                        if (resultBeanList != null && resultBeanList.size() > 0 && StringUtil.isNotBlank(id)) {
                            for (ResDoctorCost.ResultBean resBean : resultBeanList) {
                                if (code.equals(resBean.getDOCTOR_ID()) && name.equals(resBean.getNAME())) {
                                    orderPrice = resBean.getPRICE();
                                    ref_regtype = resBean.getREGISTER_TYPE();
                                    ipTvPayAmount.setText(StringUtil.endTwoChar(orderPrice));
                                    prgDialog.closeDialog();
                                    break;
                                }
                            }
                            if (orderPrice == 0) {
                                T.ShowToast(mContext, "暂无医生门诊费用信息");
                                prgDialog.closeDialog();
                            }
                        } else {
                            T.ShowToast(mContext, "暂无医生门诊费用信息" + resDoctorCost.getMessage());
                            prgDialog.closeDialog();
                        }
                    } else {
                        T.ShowToast(mContext, "获取医生门诊费用信息失败" + resDoctorCost.getMessage());
                        prgDialog.closeDialog();
                    }
                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                    prgDialog.closeDialog();
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "获取医生门诊费用失败");
                prgDialog.closeDialog();
            }
        });

    }

    @OnClick({R.id.ip_rl_wx, R.id.ip_rl_alipay, R.id.ip_btn_pay, R.id.apa_ll_call})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ip_btn_pay:
                //生成订单 后跳转到支付界面
                //拼凑订单Data

                String note = apaEtNote.getText().toString().trim();


                UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
                if (resultBean != null && StringUtil.isBlank(resultBean.getIC_NO())) {
                    T.ShowToast(mContext, "请添加就诊人信息");
                    return;
                }
                if (orderPrice == 0) {
                    T.ShowToast(mContext, "金额错误，稍后重试");
                    return;
                }

                if (StringUtil.isNotBlank(date) && date.length() > 8) {
                    date = date.substring(0, 10);
                }
                orderData = "{\"department\":\"" + dptId + "\",\"department_name\":\"" + dptName + "\",\"is_vip\":\"" + ShareInfoUtil.readResultBean(mContext).getRebateType() + "\",\"doctor\":\"" + code + "\",\"doctor_name\":\"" + name + "\",\"date\":\"" + date + "\",\"ref_regtype\":\"" + ref_regtype + "\",\"note\":\"" + note + "\",\"user_name\":\"" + ShareInfoUtil.readResultBean(mContext).getName() + "\",\"user_telephone\":\"" + ShareInfoUtil.readResultBean(mContext).getMobile() + "\"}";
//                orderData = "{\"department\":\"" + dptId + "\",\"doctor\":\"" + id + "\",\"date\":\"" + date + "\",\"ref_regtype\":\"" + ref_regtype + "\"}";
                HashMap<String, String> params = new HashMap<>();

                params.put("token", ShareInfoUtil.readToken(mContext));
                params.put("name", orderName);
                params.put("price", StringUtil.endTwoChar(orderPrice));
//                params.put("price", "" + 0.01);
                params.put("amount", orderCount);
                params.put("data", orderData);
                if (bpayType == 1) {
                    params.put("way", "wechat");
                } else if (bpayType == 2) {
                    params.put("way", "alipay");
                }
                System.out.println(params.toString());
                prgDialog = new PrgDialog(mContext);
                HttpUtil.doHttp(HttpContanst.CREATE_ORDER_DOCTOR, params, new HttpUtil.IHttpResult() {
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
                                    bundle.putString(PayActivity.projectType, "0");
                                    ProjectApplication.intentManager.toPayActivity(bundle);
                                } else {
                                    T.ShowToast(mContext,resWxpayQuick.getMessage());
                                }
                            } else if (bpayType == 2) {
                                ResPayQuick resPayQuick = GsonTools.getBean(result, ResPayQuick.class);
                                if (resPayQuick.getStatus() == 0) {
//                                    T.ShowToast(mContext, "创建订单成功跳转到支付宝支付界面。。。");
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("payType", Contanst.PAYTYPE_ALPAY);
                                    bundle.putString(PayActivity.projectType, "0");
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
            case R.id.apa_ll_call:
                CallManager.callPhone(this, ShareInfoUtil.readPhone(this));
                break;
            case R.id.ip_rl_wx:
                ip_rb_wx.setChecked(true);
                ip_rb_alpay.setChecked(false);
                bpayType = Contanst.PAYTYPE_WX;
                break;
            case R.id.ip_rl_alipay:
                ip_rb_alpay.setChecked(true);
                ip_rb_wx.setChecked(false);
                bpayType = Contanst.PAYTYPE_ALPAY;
                break;
        }
    }
}
