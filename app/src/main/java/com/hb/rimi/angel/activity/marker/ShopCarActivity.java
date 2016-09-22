package com.hb.rimi.angel.activity.marker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.pay.PayActivity;
import com.hb.rimi.angel.adapter.CarAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.CarInfo;
import com.hb.rimi.angel.bean.OrderIdCount;
import com.hb.rimi.angel.bean.PayOrderedBean;
import com.hb.rimi.angel.bean.ResPayQuick;
import com.hb.rimi.angel.bean.ResWxpayQuick;
import com.hb.rimi.angel.contanst.Contanst;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.http.UpdateTotalInfo;
import com.hb.rimi.angel.sql.CarDbUtils;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.OpenListView;
import com.hb.rimi.angel.view.PrgDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购物车
 */
public class ShopCarActivity extends AppCompatActivity implements View.OnClickListener, UpdateTotalInfo {

    public static String fromTypeCarValue = "1";//1购物车 2未支付 3已支付
    public static String fromTypeNoPayValue = "2";//1购物车 2未支付 3已支付
    public static String fromTypePayedValue = "3";//1购物车 2未支付 3已支付
    public static String fromTypeKey = "fromTypeValue";//1购物车 2未支付 3已支付
    private static String fromTypeValue;//1购物车 2未支付 3已支付
    @Bind(R.id.ssa_toolbar)
    Toolbar ssaToolbar;
    @Bind(R.id.ssa_listview)
    OpenListView ssaListView;
    List<CarInfo> carInfoList = new ArrayList<>();
    CarAdapter adapter;
    @Bind(R.id.ip_rl_wx)
    RelativeLayout ipRlWx;
    @Bind(R.id.ip_rl_alipay)
    RelativeLayout ipRlAlipay;
    @Bind(R.id.ip_rb_alpay)
    RadioButton ip_rb_alpay;
    @Bind(R.id.ip_rb_wx)
    RadioButton ip_rb_wx;
    @Bind(R.id.ip_tv_pay_amount)
    TextView ip_tv_pay_amount;
    @Bind(R.id.ip_btn_pay)
    Button ip_btn_pay;
    //登录
    @Bind(R.id.include_pay)
    LinearLayout include_pay;
    //订单参数
    String fproductIds = "";
    double ftotalPrice = 0;
    String fnames = "";
    List<OrderIdCount.ProductListBean> orderIdCounts = new ArrayList<>();
    OrderIdCount orderIdsBean = new OrderIdCount();
    List<CarInfo> noPayCarInfos = new ArrayList<>();
    @Bind(R.id.tv_shopcar)
    TextView tvShopcar;
    private Context mContext;
    private int bpayType = 1;//  1微信支付 2支付宝支付
    private PrgDialog prgDialog;
    private List<PayOrderedBean.ResultBean.ListBean> nopayLists = new ArrayList<>();
    private List<PayOrderedBean.ResultBean.ListBean> payLists = new ArrayList<>();
    private int pso0, pso1;
    private String repayId = "";//大订单id再次支付用到

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_car);
        ButterKnife.bind(this);
        setAppBar();
        initIntent();
        initData();
    }

    private void initIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromTypeValue = bundle.getString(fromTypeKey);
            pso0 = bundle.getInt("pso0");
            pso1 = bundle.getInt("pso1");
        }
    }

    private void setAppBar() {
        mContext = this;
        ssaToolbar.setTitle("");
        setSupportActionBar(ssaToolbar);
        ssaToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        ssaToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        ssaToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        String token = ShareInfoUtil.readToken(mContext);
        if (fromTypeCarValue.equals(fromTypeValue)) {
            //显示所有
            include_pay.setVisibility(View.VISIBLE);
            tvShopcar.setText("购物车");
            if (carInfoList != null && carInfoList.size() > 0) {
                carInfoList.clear();
            }
            List<CarInfo> cars = CarDbUtils.selectAll();

            if (cars != null && cars.size() > 0) {
                carInfoList.addAll(cars);
            } else {
                return;
            }
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(cars.get(i).toString());
            }
            if (adapter == null) {
                adapter = new CarAdapter(mContext, carInfoList, ShopCarActivity.this, fromTypeValue);
                ssaListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                adapter.notifyDataSetChanged();
            }
        } else if ("false".equals(fromTypeValue)) {//未支付
            tvShopcar.setText("支付确认");
            //隐藏左侧checkbox 和数量加减
            include_pay.setVisibility(View.VISIBLE);
            //加载为支付得数据
            HttpUtil.doHttp(HttpContanst.PAY_ORDER_TWO_LIST + token + "&status=0&types=0", null, new HttpUtil.IHttpResult() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                    PayOrderedBean payOrderListBean = GsonTools.getBean(result, PayOrderedBean.class);
                    int status = payOrderListBean.getStatus();
                    if (status == 0) {
                        PayOrderedBean.ResultBean result1 = payOrderListBean.getResult();
                        nopayLists = result1.getList();
                        double tPrice = 0;
                        System.out.println(pso0 + "nopayLists==>" + nopayLists.size());
                        PayOrderedBean.ResultBean.ListBean listBean = nopayLists.get(pso0);
                        tPrice = listBean.getPrice();
                        int len = listBean.getDetails().size();
                        repayId = listBean.getId();
                        for (int i = 0; i < len; i++) {
                            CarInfo carinfo = new CarInfo();
                            carinfo.setProduct_id(listBean.getDetails().get(i).getProductId());
                            carinfo.setGoodName(listBean.getDetails().get(i).getProductName());
                            carinfo.setSpecifications(listBean.getDetails().get(i).getType());
                            carinfo.setCount(listBean.getDetails().get(i).getAmount());
                            carinfo.setPrice(listBean.getDetails().get(i).getPrice());
                            noPayCarInfos.add(carinfo);
                        }
                        ip_tv_pay_amount.setText("" + tPrice);
                        if (adapter == null) {
                            adapter = new CarAdapter(mContext, noPayCarInfos, ShopCarActivity.this, fromTypeValue);
                            ssaListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        T.ShowToast(mContext, payOrderListBean.getMessage().toString());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    T.ShowToast(mContext, "网络错误或连接失败");
                }
            });


        } else if ("true".equals(fromTypeValue)) {//已支付
            //隐藏左侧checkbox 和数量加减 和 支付界面
            include_pay.setVisibility(View.GONE);
            tvShopcar.setText("订单详情");
            HttpUtil.doHttp(HttpContanst.PAY_ORDER_TWO_LIST + token + "&status=1&types=0", null, new HttpUtil.IHttpResult() {
                @Override
                public void onSuccess(String result) {
                    PayOrderedBean payOrderListBean = GsonTools.getBean(result, PayOrderedBean.class);
                    int status = payOrderListBean.getStatus();
                    if (status == 0) {
                        PayOrderedBean.ResultBean result1 = payOrderListBean.getResult();
                        payLists = result1.getList();
                        List<CarInfo> payCarInfos = new ArrayList<>();


                        PayOrderedBean.ResultBean.ListBean listBean = payLists.get(pso1);
                        int len = listBean.getDetails().size();
                        for (int i = 0; i < len; i++) {
                            CarInfo carinfo = new CarInfo();
                            carinfo.setProduct_id(listBean.getDetails().get(i).getProductId());
                            carinfo.setGoodName(listBean.getDetails().get(i).getProductName());
                            carinfo.setSpecifications(listBean.getDetails().get(i).getType());
                            carinfo.setPrice(listBean.getDetails().get(i).getPrice());
                            carinfo.setCount(listBean.getDetails().get(i).getAmount());
                            payCarInfos.add(carinfo);
                        }

                        if (adapter == null) {
                            adapter = new CarAdapter(mContext, payCarInfos, ShopCarActivity.this, fromTypeValue);
                            ssaListView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        T.ShowToast(mContext, payOrderListBean.getMessage().toString());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    T.ShowToast(mContext, "网络错误或连接失败");
                    ex.printStackTrace();
                }
            });

        }
        ssaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
//                if (arg2 != 0) {
//                    arg2 = arg2 - 1;
//                }
                // Toast.makeText(SelectStoreActivity.this, "" + arg2,
                // Toast.LENGTH_SHORT).show();
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
                CarAdapter.ViewHolder holder = (CarAdapter.ViewHolder) arg1.getTag();
                // 改变CheckBox的状态
                holder.item_package_chk.toggle();
                // 将CheckBox的选中状况记录下来
                CarAdapter.getIsSelected().put(arg2, holder.item_package_chk.isChecked());


                showTotalInfo();


            }
        });
        ip_rb_wx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ip_rb_wx.setBackgroundResource(R.mipmap.icon_radiao_checked);
                    ip_rb_alpay.setBackgroundResource(R.mipmap.icon_radiao_no_checked);
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
                    ip_rb_wx.setBackgroundResource(R.mipmap.icon_radiao_no_checked);
                    ip_rb_alpay.setBackgroundResource(R.mipmap.icon_radiao_checked);
                    ip_rb_alpay.setChecked(true);
                    ip_rb_wx.setChecked(false);
                    bpayType = Contanst.PAYTYPE_ALPAY;
                }
            }
        });

    }

    public void showTotalInfo() {
        OrderIdCount.ProductListBean orderIdCount;
        String productIds = "";
        double totalPrice = 0;
        String names = "";
        if (orderIdCounts != null && orderIdCounts.size() > 0) {
            orderIdCounts.clear();
        }
        for (int i = 0; i < carInfoList.size(); i++) {

            if (CarAdapter.getIsSelected().get(i)) {
                orderIdCount = new OrderIdCount.ProductListBean();
                orderIdCount.setAmount("" + CarAdapter.tempNums.get(i));
                orderIdCount.setProduct_id("" + carInfoList.get(i).getProduct_id());
                orderIdCounts.add(orderIdCount);

                carInfoList.get(i).toString();
                //提升精度
                BigDecimal a = new BigDecimal(carInfoList.get(i).getPrice() * CarAdapter.tempNums.get(i));
                BigDecimal b = new BigDecimal(totalPrice);
                totalPrice = a.add(b).doubleValue();

                if ("".equals(productIds)) {
                    productIds = productIds
                            + ((CarInfo) adapter.getItem(i))
                            .getId();
                    names = names
                            + ((CarInfo) adapter.getItem(i))
                            .getGoodName();
                } else {
                    productIds = productIds
                            + ","
                            + ((CarInfo) adapter.getItem(i))
                            .getId();
                    names = names
                            + "_"
                            + ((CarInfo) adapter.getItem(i))
                            .getGoodName();
                }
            }
        }
        System.out.println("" + totalPrice);
        if (totalPrice < 0) {
            ftotalPrice = 0;
            totalPrice = 0;
        }
        ip_tv_pay_amount.setText(StringUtil.endTwoChar(totalPrice));

        fproductIds = productIds;
        ftotalPrice = totalPrice;
        fnames = names;
        orderIdsBean.setProduct_list(orderIdCounts);
    }

    @OnClick({R.id.ip_rl_wx, R.id.ip_rl_alipay, R.id.ip_btn_pay})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ip_rl_wx:
                ip_rb_wx.setBackgroundResource(R.mipmap.icon_radiao_checked);
                ip_rb_alpay.setBackgroundResource(R.mipmap.icon_radiao_no_checked);
                ip_rb_wx.setChecked(true);
                ip_rb_alpay.setChecked(false);
                bpayType = Contanst.PAYTYPE_WX;
                break;
            case R.id.ip_rl_alipay:
                ip_rb_wx.setBackgroundResource(R.mipmap.icon_radiao_no_checked);
                ip_rb_alpay.setBackgroundResource(R.mipmap.icon_radiao_checked);
                ip_rb_alpay.setChecked(true);
                ip_rb_wx.setChecked(false);
                bpayType = Contanst.PAYTYPE_ALPAY;
                break;
            case R.id.ip_btn_pay:



                try {
                    double finalPrice = Double.valueOf(ip_tv_pay_amount.getText().toString());
                    if (finalPrice <= 0) {
                        T.ShowToast(mContext, "请选择要支付商品");
                        return;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    T.ShowToast(mContext, "请选择要支付商品");
                    return;
                }

                if ("false".equals(fromTypeValue)) {
                    if (StringUtil.isBlank(repayId)) {
                        T.ShowToast(mContext, "订单支付失败");
                        return;
                    }
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("token", ShareInfoUtil.readToken(mContext));
                    params.put("payOrderId", repayId);
                    if (bpayType == Contanst.PAYTYPE_WX) {
                        params.put("way", "wechat");
                    } else if (bpayType == Contanst.PAYTYPE_ALPAY) {
                        params.put("way", "alipay");
                    }
                    System.out.println(params.toString());

                    prgDialog = new PrgDialog(mContext);
                    HttpUtil.doHttp(HttpContanst.ORDER_REPAY, params, new HttpUtil.IHttpResult() {
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
                                        T.ShowToast(mContext, resWxpayQuick.getMessage());
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
                } else if (fromTypeCarValue.equals(fromTypeValue)) {
                    if (ftotalPrice == 0) {
                        T.ShowToast(mContext, "没有选择商品");
                        return;
                    }
                    if (carInfoList == null) {
                        T.ShowToast(mContext, "没有选择商品");
                        return;
                    }
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("token", ShareInfoUtil.readToken(mContext));
                    params.put("name", fnames);
                    params.put("price", "" + ftotalPrice);
                    params.put("amount", "" + orderIdCounts.size());
                    String orderData = GsonTools.getStringOfBean(orderIdsBean);
                    params.put("data", orderData);

                    if (bpayType == Contanst.PAYTYPE_WX) {
                        params.put("way", "wechat");
                    } else if (bpayType == Contanst.PAYTYPE_ALPAY) {
                        params.put("way", "alipay");
                    }
                    System.out.println(params.toString());

                    prgDialog = new PrgDialog(mContext);
                    HttpUtil.doHttp(HttpContanst.MARKER_ORDER_QUICK, params, new HttpUtil.IHttpResult() {
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
                                        T.ShowToast(mContext, resWxpayQuick.getMessage());
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
                }
                break;
        }
    }

    @Override
    public void updateInfo() {
        showTotalInfo();
    }
}
