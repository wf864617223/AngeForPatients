package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.pay.PayActivity;
import com.hb.rimi.angel.adapter.BAppointmentTimeAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.BAppointmentTime;
import com.hb.rimi.angel.bean.ResBAppointmentTime;
import com.hb.rimi.angel.bean.ResPayQuick;
import com.hb.rimi.angel.bean.ResWxpayQuick;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.SortToolNew;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.util.VocationalUtil;
import com.hb.rimi.angel.view.PrgDialog;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * B超预约支付
 */
public class BAppointmentPayActivity extends AppCompatActivity implements View.OnClickListener {
    public static String orderDate;
    public static EditText baa_et_input_date;
    @Bind(R.id.baa_tv_project)
    TextView baa_tv_project;
    @Bind(R.id.baa_toolbar)
    Toolbar baa_toolbar;
    //@Bind(R.id.imci_ll_card_info)
    //LinearLayout imci_ll_card_info;
    @Bind(R.id.ip_rb_alpay)
    RadioButton ip_rb_alpay;
    @Bind(R.id.ip_rb_wx)
    RadioButton ip_rb_wx;
    //末次月经
    //@Bind(R.id.imci_ll_last_menses)
    //LinearLayout imci_ll_last_menses;
    @Bind(R.id.baa_recyclerView)
    RecyclerView baa_recyclerView;
    @Bind(R.id.ba_ll_call)
    LinearLayout baLlCall;
    @Bind(R.id.ba_tv_time_notice)
    TextView baTvTimeNotice;
    @Bind(R.id.ip_tv_pay_amount)
    TextView ipTvPayAmount;
    @Bind(R.id.ip_tv_symbol)
    TextView ipTvSymbol;
    @Bind(R.id.ip_iv_wx)
    ImageView ipIvWx;
    @Bind(R.id.ip_iv_alpay)
    ImageView ipIvAlpay;
    @Bind(R.id.ip_btn_pay)
    Button ipBtnPay;
    @Bind(R.id.baa_et_input_date)
    EditText baaEtInputDate;
    //    @Bind(R.id.baa_recyclerView)
    //    RecyclerView baaRecyclerView;

    @Bind(R.id.baa_tv_project_des)
    TextView baa_tv_project_des;
    @Bind(R.id.baa_tv_des_detail)
    TextView baa_tv_des_detail;
    @Bind(R.id.bpa_title)
    TextView bpa_title;
    @Bind(R.id.ba_et_note)
    EditText baEtNote;

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
    ImageView lui_iv_add;
    //    @Bind(R.id.baa_recyclerView)
//    RecyclerView baaRecyclerView;
//    @Bind(R.id.ip_rb_alpay)
//    RadioButton ipRbAlpay;
//
    @Bind(R.id.ip_rl_wx)
    RelativeLayout ipRlWx;
    @Bind(R.id.ip_rl_alipay)
    RelativeLayout ipRlAlipay;
    //    private int orderPrice = 0;//订单价格整型
    private String startT;
    private String endT;

    private String item_code;
    private String item_name;
    private double item_price;//订单价格整型
    private double item_id;
    private String describe;
    private String describe_detail;
    private Context mContext;
    private List<BAppointmentTime> bAppointmentTimeList;
    private BAppointmentTimeAdapter bAppointmentTimeAdapter;
    private int bpayType = 1;//  1微信支付 2支付宝支付
    private PrgDialog prgDialog;
    private String orderName = "";// 项目名称
    private String orderCount = "1";// 商品数量默认为1
    private String orderData = "";
    private String end_date = "";//到院时间
    private String wdyy_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_bappointment);
        ButterKnife.bind(this);
        baa_et_input_date = (EditText) findViewById(R.id.baa_et_input_date);
        initIntent();
        initData();
        initList();
    }

    private void initList() {
        HashMap<String, String> params = new HashMap<String, String>();
//        item_name = "二级B超";
        params.put("action", "GetProjectList");
        params.put("project", item_name);
        HttpUtil.doHttp(HttpContanst.PROJECT_WEEK, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {

                if (StringUtil.isNotBlank(result)) {
                    ResBAppointmentTime resBean = GsonTools.getBean(result, ResBAppointmentTime.class);
                    if (resBean.getStatus() == 0) {
                        bAppointmentTimeList = resBean.getResult();
                        bAppointmentTimeAdapter = new BAppointmentTimeAdapter(mContext, bAppointmentTimeList);
                        baa_recyclerView.setAdapter(bAppointmentTimeAdapter);
                        //倒序排序
                        if (bAppointmentTimeList != null && bAppointmentTimeList.size() > 0) {

                            SortToolNew sort = new SortToolNew();
                            Collections.sort(bAppointmentTimeList, sort);


                            startT = bAppointmentTimeList.get(bAppointmentTimeList.size() - 1).getDate();
                            endT = bAppointmentTimeList.get(0).getDate();


                        }
                        bAppointmentTimeAdapter.notifyDataSetChanged();
                    } else {
                        T.ShowToast(mContext, "获取一周的预约信息失败" + resBean.getMessage(), 0);
                    }
                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
            }
        });
    }

    private void initData() {
        mContext = BAppointmentPayActivity.this;
        baa_toolbar.setTitle("");
        setSupportActionBar(baa_toolbar);
        baa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        baa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        baa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置recycelerView的布局管理器
        baa_recyclerView.setLayoutManager(linearLayoutManager);
        //添加默认分割线
//        baa_recyclerView.addItemDecoration(new DividerItemDecoration(this));

        orderName = item_name;
        //初始化
//        bpa_title.setText(item_name);
//        baa_tv_project.setText(item_name);
        ipTvPayAmount.setText(StringUtil.endTwoChar(item_price));
        baa_tv_project_des.setText(describe);
        ip_rb_wx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ip_rb_wx.setChecked(true);
                    ip_rb_alpay.setChecked(false);
                    bpayType = Contanst.PAYTYPE_WX;
                }
            }
        });

        ip_rb_alpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ip_rb_alpay.setChecked(true);
                    ip_rb_wx.setChecked(false);
                    bpayType = Contanst.PAYTYPE_ALPAY;
                }
            }
        });
        //判断
        if (VocationalUtil.hasIcNo(mContext)) {
            luiTvNotice.setVisibility(View.GONE);
            luiTvName.setText(ShareInfoUtil.readResultBean(mContext).getName());
            if ("1".equals(ShareInfoUtil.readResultBean(mContext).getSex())) {
                luiTvSex.setText("男");
            } else if ("0".equals(ShareInfoUtil.readResultBean(mContext).getSex())) {
                luiTvSex.setText("女");
            }

            luiTvBirth.setText(ShareInfoUtil.readResultBean(mContext).getBirthday());
            if (VocationalUtil.hasMenses(mContext)) {
                luiTvLastMenes.setText(ShareInfoUtil.readResultBean(mContext).getMenses());
                lui_iv_add.setVisibility(View.GONE);
            } else {

                lui_iv_add.setVisibility(View.VISIBLE);
            }
        } else {
            lui_iv_add.setVisibility(View.VISIBLE);

        }
        //如果到院时间不为空 那么说明是从网电预约过来的
        if (StringUtil.isNotBlank(end_date)) {
            baa_et_input_date.setText(end_date);
            baa_et_input_date.setClickable(false);
            baa_et_input_date.setEnabled(false);
        }
    }


    private void initIntent() {
        try {
            item_code = getIntent().getExtras().getString("item_code");
            item_name = getIntent().getExtras().getString("item_name");
            item_price = getIntent().getExtras().getDouble("item_price");
            item_id = getIntent().getExtras().getDouble("item_id");

            describe = getIntent().getExtras().getString("describe");
            describe_detail = getIntent().getExtras().getString("describe_detail");

            //到院时间
            end_date = getIntent().getExtras().getString("end_date");
            wdyy_id = getIntent().getExtras().getString("wdyy_id");



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ip_rl_wx, R.id.ip_rl_alipay, R.id.ba_ll_call, R.id.ip_btn_pay, R.id.baa_et_input_date, R.id.baa_tv_des_detail, R.id.lv_add_userinfo})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lv_add_userinfo:
                Intent intentadd = new Intent(mContext, AddUserInfoActivity.class);
                Bundle bundleadd = new Bundle();
                bundleadd.putString("targetType", "0");
                bundleadd.putString("item_code", item_code);
                bundleadd.putString("item_id", "" + item_id);
                bundleadd.putString("item_name", item_name);
                bundleadd.putString("item_price", "" + item_price);
                intentadd.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentadd.putExtras(bundleadd);
                startActivityForResult(intentadd, 2);
                break;
            case R.id.baa_tv_des_detail:
                if (StringUtil.isBlank(describe_detail)) {
                    T.ShowToast(mContext, "暂无更多详情");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("detail", describe_detail);
                bundle.putString("title", item_name);
                ProjectApplication.intentManager.toDesDetailActivity(bundle);

                break;
            case R.id.ba_ll_call:
                CallManager.callPhone(this, ShareInfoUtil.readPhone(this));
                break;
            case R.id.ip_btn_pay:
                String dt = baa_et_input_date.getText().toString().trim();
                String note = baEtNote.getText().toString().trim();
                orderDate = dt;
                if (StringUtil.isNotBlank(orderDate)) {
                    orderDate = orderDate.substring(0, 10);
                } else {
                    T.ShowToast(mContext, "预约日期不能为空");
                    return;
                }

                UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
                if (resultBean != null && StringUtil.isBlank(resultBean.getIC_NO())) {
                    T.ShowToast(mContext, "请添加就诊人信息");
                    return;
                }
                if (resultBean != null && StringUtil.isBlank(resultBean.getMenses())) {
                    T.ShowToast(mContext, "请添加末次月经");
                    return;
                }

//                item_price = 0.01;
                if (item_price == 0) {
                    T.ShowToast(mContext, "金额错误，稍后重试");
                    return;
                }
                if(StringUtil.isNotBlank(end_date)){

                    orderData = "{\"appointment\":\"" + item_id + "\",\"appointment_name\":\"" + item_name + "\",\"is_vip\":\"" + ShareInfoUtil.readResultBean(mContext).getRebateType() + "\",\"note\":\"" + note + "\",\"user_name\":\"" + ShareInfoUtil.readResultBean(mContext).getName() + "\",\"user_telephone\":\"" + ShareInfoUtil.readResultBean(mContext).getMobile() + "\",\"date\":\"" + orderDate + "\"" + ",\"wdyy_id\":\""+wdyy_id+"\"}";
                }else{
                    orderData = "{\"appointment\":\"" + item_id + "\",\"appointment_name\":\"" + item_name + "\",\"is_vip\":\"" + ShareInfoUtil.readResultBean(mContext).getRebateType() + "\",\"note\":\"" + note + "\",\"user_name\":\"" + ShareInfoUtil.readResultBean(mContext).getName() + "\",\"user_telephone\":\"" + ShareInfoUtil.readResultBean(mContext).getMobile() + "\",\"date\":\"" + orderDate + "\"" + "}";
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("token", ShareInfoUtil.readToken(mContext));
                params.put("name", orderName);
                params.put("price", StringUtil.endTwoChar(item_price));
                params.put("amount", orderCount);
                params.put("data", orderData);

                if (bpayType == 1) {
                    params.put("way", "wechat");
                } else if (bpayType == 2) {
                    params.put("way", "alipay");
                }

                System.out.println(params.toString());

                prgDialog = new PrgDialog(mContext);
                HttpUtil.doHttp(HttpContanst.CREATE_ORDER_CHECK, params, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        if (result != null) {
//                            T.ShowToast(mContext, "创建订单成功跳转到支付界面。。。");
                            if (bpayType == 1) {//微信
                                ResWxpayQuick resWxpayQuick = GsonTools.getBean(result, ResWxpayQuick.class);
                                if (resWxpayQuick.getStatus() == 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("payType", Contanst.PAYTYPE_WX);
                                    bundle.putString(PayActivity.keyResult, result);
                                    bundle.putString(PayActivity.projectType, "1");
                                    ProjectApplication.intentManager.toPayActivity(bundle);
                                } else {
                                    T.ShowToast(mContext, resWxpayQuick.getMessage());
                                }
                            } else if (bpayType == 2) {
                                ResPayQuick resPayQuick = GsonTools.getBean(result, ResPayQuick.class);
                                if (resPayQuick.getStatus() == 0) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("payType", Contanst.PAYTYPE_ALPAY);
                                    bundle.putString("alipayContruct", resPayQuick.getResult().getAlipayContruct());
                                    bundle.putString(PayActivity.projectType, "1");
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
            case R.id.baa_et_input_date:
//                DateChooseUtils sdateChooseUtils = new DateChooseUtils(mContext, baa_et_input_date);
//                sdateChooseUtils.getdate();

                if (StringUtil.isBlank(startT) || StringUtil.isBlank(endT)) {
                    T.ShowToast(mContext, "暂无可预约时间");
                    return;
                }
                Intent intent = new Intent(mContext, DateActivity.class);
                intent.putExtra("startT", startT);
                intent.putExtra("endT", endT);


                intent.putExtra("bAppointmentTimeList", (Serializable) bAppointmentTimeList);
                System.out.println("startT=>" + startT + "--" + "endT" + endT);

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
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

    @Override
    protected void onResume() {
        super.onResume();
        try {

            System.out.print("b_name==" + ShareInfoUtil.readParams(mContext, "b_name") + "b_userSex==" + ShareInfoUtil.readParams(mContext, "b_userSex"));
            UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
            String b_name = resultBean.getName();
            String b_birthday = resultBean.getBirthday();
            String b_userSex = resultBean.getSex();
            String b_menses = resultBean.getMenses();

            luiTvName.setText(b_name);

            if ("1".equals(b_userSex)) {
                luiTvSex.setText("男");
            } else if ("0".equals(b_userSex)) {
                luiTvSex.setText("女");
            }
            //883410ac7861b242694045a1d3a641fd
            luiTvBirth.setText(b_birthday);
//            String mens = ShareInfoUtil.readParams(mContext, "b_menses");
            if (StringUtil.isNotBlank(b_menses)) {
                luiTvLastMenes.setText(b_menses);
                lui_iv_add.setVisibility(View.GONE);
            } else {
                lui_iv_add.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String selectDate = bundle.getString("selectDate").trim();
                    baa_et_input_date.setText(selectDate.substring(0, 10));
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String addSuc = "";
                    String updateSuc = "";
                    if (bundle != null) {
                        addSuc = bundle.getString("addSuc");
                        updateSuc = bundle.getString("updateSuc");
                    }


                    System.out.print("b_name==" + ShareInfoUtil.readParams(mContext, "b_name") + "b_userSex==" + ShareInfoUtil.readParams(mContext, "b_userSex"));
                    luiTvName.setText(ShareInfoUtil.readParams(mContext, "b_name"));

                    if ("1".equals(ShareInfoUtil.readParams(mContext, "b_userSex"))) {
                        luiTvSex.setText("男");
                    } else if ("0".equals(ShareInfoUtil.readParams(mContext, "b_userSex"))) {
                        luiTvSex.setText("女");
                    }
                    //883410ac7861b242694045a1d3a641fd
                    luiTvBirth.setText(ShareInfoUtil.readParams(mContext, "b_birthday"));
                    String mens = ShareInfoUtil.readParams(mContext, "b_menses");


                    if (StringUtil.isNotBlank(mens)) {
                        luiTvLastMenes.setText(mens);
                        lui_iv_add.setVisibility(View.GONE);
                    } else {
                        lui_iv_add.setVisibility(View.VISIBLE);
                    }
                }
                break;

        }
    }
}
