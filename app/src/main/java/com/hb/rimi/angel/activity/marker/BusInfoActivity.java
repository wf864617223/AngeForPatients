package com.hb.rimi.angel.activity.marker;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.pay.PayActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.BusInfoBean;
import com.hb.rimi.angel.bean.CarInfo;
import com.hb.rimi.angel.bean.ResPayQuick;
import com.hb.rimi.angel.bean.ResWxpayQuick;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.sql.CarDbUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BusInfoActivity extends AppCompatActivity {

    @Bind(R.id.iv_bus_img)
    ImageView ivBusImg;
    @Bind(R.id.tv_now_price)
    TextView tvNowPrice;
    @Bind(R.id.tv_old_price)
    TextView tvOldPrice;
    @Bind(R.id.tv_buy_person)
    TextView tvBuyPerson;
    @Bind(R.id.tv_bus_name)
    TextView tvBusName;
    @Bind(R.id.tv_bus_indu)
    TextView tvBusIndu;
    @Bind(R.id.tv_choose_num)
    TextView tvChooseNum;
    @Bind(R.id.iv_bus_add)
    ImageView btnBusAdd;
    @Bind(R.id.tv_bus_num)
    TextView tvBusNum;
    @Bind(R.id.iv_bus_jian)
    ImageView btnBusJian;
    @Bind(R.id.tv_productName)
    TextView tvProductName;
    @Bind(R.id.tv_brand)
    TextView tvBrand;
    @Bind(R.id.tv_countryOrigin)
    TextView tvCountryOrigin;
    @Bind(R.id.tv_suitableCrowd)
    TextView tvSuitableCrowd;
    @Bind(R.id.btn_now_buy)
    Button btnNowBuy;
    @Bind(R.id.btn_add_car)
    Button btnAddCar;
    @Bind(R.id.shop_info_toolbar)
    Toolbar shopInfoToolbar;

    Context context;
    @Bind(R.id.ll_add_text)
    RadioGroup llAddText;
    BusInfoBean.ResultBean bean;
    private String busnum;
    private int num;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    num = Integer.parseInt(busnum);
                    num++;
                    tvBusNum.setText(num + "");
                    break;
                case 2:
                    num = Integer.parseInt(busnum);
                    num--;
                    if (num < 1) {
                        T.ShowToast(BusInfoActivity.this, "购买数量最少一件");
                        num = 1;
                    }
                    tvBusNum.setText(num + "");
                    break;
            }
            return true;
        }
    });
    private int pid;
    private String token;
    private RadioButton radioButton;
    private AlertDialog myDialog = null;
    private double tradePrice;
    private PrgDialog prgDialog;
    private String orderData = "";
    private String productId;
    private double tradePrice1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);
        ButterKnife.bind(this);
        context = BusInfoActivity.this;
        token = ShareInfoUtil.readToken(context);
        initToolBar();
        initData();
    }

    private void initToolBar() {
        shopInfoToolbar.setTitle("");
        setSupportActionBar(shopInfoToolbar);
        shopInfoToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        shopInfoToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        shopInfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        final Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        System.out.println("productId1==>" + productId);
        prgDialog = new PrgDialog(context);
        HttpUtil.doHttp(HttpContanst.BUS_INFO + productId, null, new HttpUtil.IHttpResult() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(String result) {
                BusInfoBean busInfoBean = GsonTools.getBean(result, BusInfoBean.class);
                int status = busInfoBean.getStatus();
                String message = busInfoBean.getMessage();
                if (status == 0 || "success".equals(message)) {
                    bean = busInfoBean.getResult();
                    String image = bean.getImage();
                    String url = HttpContanst.SERVER_ADD + image;
                    Glide.with(BusInfoActivity.this).load(url).placeholder(R.mipmap.nopicture).error(R.mipmap.nopicture).into(ivBusImg);
                    pid = bean.getId();
                    System.out.println("pid==>" + pid);
                    tvBusName.setText(bean.getName());
                    tvBuyPerson.setText(bean.getSales() + "人已经购买");
                    tvBusIndu.setText(bean.getIntroduction());
                    tradePrice = bean.getTradePrice();
                    tradePrice1 = bean.getTradePrice();
                    tvNowPrice.setText("" + bean.getTradePrice());
                    tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                    tvOldPrice.setText("" + bean.getPrice());
                    tvProductName.setText(bean.getProductName());
                    tvBrand.setText(bean.getBrand());
                    tvCountryOrigin.setText(bean.getCountryOrigin());
                    tvSuitableCrowd.setText(bean.getSuitableCrowd());
                    String productType = bean.getProductType();
                    String[] split = productType.split("_");
                    for (int i = 0; i < split.length; i++) {
                        radioButton = new RadioButton(context);
                        radioButton.setText(split[i]);
                        radioButton.setPadding(5, 5, 5, 5);
                        radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
                        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    radioButton.setBackground(getResources().getDrawable(R.drawable.shape));
                                    radioButton.setTextColor(getResources().getColor(R.color.white));
                                } else {
                                    radioButton.setBackground(getResources().getDrawable(R.color.color_hint));
                                    radioButton.setBackgroundColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        llAddText.addView(radioButton);
                    }
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(context, "数据错误，请重试");
                prgDialog.closeDialog();
            }
        });
        btnBusAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busnum = tvBusNum.getText().toString();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        });
        btnBusJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busnum = tvBusNum.getText().toString();
                Message msg = new Message();
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
        //商品加入购物车
        btnAddCar.setOnClickListener(new View.OnClickListener() {

            private String message;

            @Override
            public void onClick(View v) {
                //T.ShowToast(mContext,"加入购物车");
                if (!radioButton.isChecked()) {
                    T.ShowToast(context, "请先选择商品型号");
                    return;
                }
                if (CarDbUtils.hasPoductId(null, bean.getId())) {
                    T.ShowToast(BusInfoActivity.this, "您已添加此商品");
                } else {

                    CarInfo carInfo = new CarInfo();
                    carInfo.setProduct_id(bean.getId());
                    carInfo.setCount(Integer.valueOf(tvBusNum.getText().toString()));
                    carInfo.setGoodName(bean.getProductName());
                    carInfo.setPrice(bean.getTradePrice());
                    carInfo.setSpecifications(bean.getProductType());
                    System.out.println(carInfo.toString());
                    CarDbUtils.insert(carInfo);
                    T.ShowToast(BusInfoActivity.this, "商品加入成功，请到购物车查看");
                }


//                HashMap<String, String> parms = new HashMap<String, String>();
//                parms.put("product_id", pid + "");
//                parms.put("amount", tvBusNum.getText().toString());
//                parms.put("behavior", "0");
//                HttpUtil.doHttp(HttpContanst.BUS_ADD_REMOVE + token, parms, new HttpUtil.IHttpResult() {
//                    @Override
//                    public void onSuccess(String result) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(result);
//                            int status = jsonObject.optInt("status");
//                            message = jsonObject.optString("message");
//                            if (status == 0 || "success".equals(message)) {
//                                T.ShowToast(mContext, "加入购物车成功，请到我的订单中查看");
//                            } else {
//                                T.ShowToast(mContext, "加入失败，请重试");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        T.ShowToast(mContext, message.toString());
//                    }
//                });
            }
        });
        //立即购买的监听
        btnNowBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T.ShowToast(mContext,"立即购买");
                if (radioButton.isChecked()) {
                    //String s = radioButton.getText().toString();
                    //T.ShowToast(mContext,s);
                    //HashMap<String,String> parms = new HashMap<String, String>();
                    //parms.put("token",token);
                    new AlertDialog.Builder(context).setTitle("请选择支付方式").setIcon(
                            android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                            new String[]{"支付宝支付", "微信支付"}, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        T.ShowToast(context, "选择了支付宝支付");
                                        //prgDialog = new PrgDialog(mContext);
                                        HashMap<String, String> parms = new HashMap<String, String>();
                                        parms.put("token", token);
                                        parms.put("name", tvBusName.getText().toString());
                                        parms.put("price", tradePrice1 + "");
                                        parms.put("amount", tvBusNum.getText().toString());
                                        orderData = "{\"product_list\":[ {\"product_id\":\"" + productId + "\",\"amount\":\"" + tvBusNum.getText().toString() + "\"}]}";
                                        parms.put("data", orderData);
                                        System.out.println(parms.toString());
                                        parms.put("way", "alipay");
                                        System.out.println(parms.toString());
                                        HttpUtil.doHttp(HttpContanst.MARKER_ORDER_QUICK, parms, new HttpUtil.IHttpResult() {
                                            @Override
                                            public void onSuccess(String result) {
                                                ResPayQuick resPayQuick = GsonTools.getBean(result, ResPayQuick.class);
                                                if (resPayQuick.getStatus() == 0) {
//                                                  T.ShowToast(mContext, "创建订单成功跳转到支付宝支付界面。。。");
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("payType", Contanst.PAYTYPE_ALPAY);
                                                    bundle.putString(PayActivity.projectType, "0");
                                                    bundle.putString("alipayContruct", resPayQuick.getResult().getAlipayContruct());
                                                    ProjectApplication.intentManager.toPayActivity(bundle);
                                                } else {
                                                    T.ShowToast(context, resPayQuick.getMessage());
                                                }
                                                prgDialog.closeDialog();
                                            }

                                            @Override
                                            public void onError(Throwable ex, boolean isOnCallback) {
                                                T.ShowToast(context, "订单创建失败，返回数据为空", 0);
                                                prgDialog.closeDialog();
                                            }
                                        });
                                    } else {
                                        T.ShowToast(context, "选择了微信支付");
                                        HashMap<String, String> parms = new HashMap<String, String>();
                                        prgDialog = new PrgDialog(context);
                                        parms.put("token", token);
                                        parms.put("name", tvBusName.getText().toString());
                                        parms.put("price", tvNowPrice.getText().toString());
                                        parms.put("amount", tvBusNum.getText().toString());
                                        orderData = "{\"product_list\":[ {\"product_id\":\"" + productId + "\",\"amount\":\"" + tvBusNum.getText().toString() + "\"}]}";
                                        parms.put("data", orderData);
                                        System.out.println(parms.toString());
                                        parms.put("way", "wechat");
                                        HttpUtil.doHttp(HttpContanst.MARKER_ORDER_QUICK, parms, new HttpUtil.IHttpResult() {
                                            @Override
                                            public void onSuccess(String result) {
                                                ResWxpayQuick resWxpayQuick = GsonTools.getBean(result, ResWxpayQuick.class);
                                                if (resWxpayQuick.getStatus() == 0) {
                                                    //T.ShowToast(mContext, "创建订单成功跳转到微信支付界面。。。");
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("payType", Contanst.PAYTYPE_WX);
                                                    bundle.putString(PayActivity.keyResult, result);
                                                    bundle.putString(PayActivity.projectType, "0");
                                                    ProjectApplication.intentManager.toPayActivity(bundle);
                                                } else {
                                                    T.ShowToast(context, resWxpayQuick.getMessage());
                                                }
                                                prgDialog.closeDialog();
                                            }

                                            @Override
                                            public void onError(Throwable ex, boolean isOnCallback) {
                                                T.ShowToast(context, "订单创建失败，返回数据为空", 0);
                                                prgDialog.closeDialog();
                                            }
                                        });
                                    }
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("取消", null).show();
                } else {
                    T.ShowToast(context, "请先选择商品型号");
                }
            }
        });
    }
}
