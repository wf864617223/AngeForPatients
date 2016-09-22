package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.CheckPaymentAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.Order;
import com.hb.rimi.angel.bean.ResOrder;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 3 未付费订单列表
 * 分为三个步骤   1 就诊列表  2 生产订单号 3 未付费订单列表
 */
public class CheckPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    public double allCosts = 0;//总金额
    public int allCount = 0;//总数量
    @Bind(R.id.cpa_toolbar)
    Toolbar cpa_toolbar;
    @Bind(R.id.cpa_tv_tmoney)
    TextView cpa_tv_tmoney;
    @Bind(R.id.cpa_recyclerView)
    RecyclerView cpa_recyclerView;
    @Bind(R.id.pca_iv_all_list)
    ImageView pca_iv_all_list;
    @Bind(R.id.pca_rl_all_cost)
    RelativeLayout pca_rl_all_cost;
    @Bind(R.id.pca_btn_next)
    Button pca_btn_next;
    List<Order> orders = new ArrayList<Order>();
    private CheckPaymentAdapter adapter;
    private Context mContext;
    private PrgDialog prgDialog;
    private String orderId;//订单编号
    private double sumcosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_check_payment);
        ButterKnife.bind(this);
        initIntent();
        initData();
        initList();
    }

    private void initIntent() {
        orderId = getIntent().getExtras().getString("order_id", "");
        sumcosts = getIntent().getExtras().getDouble("sumcosts",-1);
    }

    private void initList() {
        mContext = CheckPaymentActivity.this;
        prgDialog = new PrgDialog(mContext);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "notorder");
        params.put("orderid", orderId);
        params.put("status", "0");//0表示未付费
        params.put("token", ShareInfoUtil.readToken(mContext));
        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
        String name = resultBean.getName();
        if("".equals(name)){
            T.ShowToast(mContext,"新用户暂无此信息");
            return;
        }
        HttpUtil.doHttp(HttpContanst.UNPAID_ORDER_LIST, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResOrder resOrder = GsonTools.getBean(result, ResOrder.class);
                    if (resOrder.getStatus() == 0) {
                        orders = resOrder.getResult();
                        //适配数据
                        adapter = new CheckPaymentAdapter(mContext, orders);
                        cpa_recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //计算总额
                        for (Order order : orders) {
                            allCosts += order.getCOSTS();
                            allCount+=order.getAMOUNT();
                        }
                        cpa_tv_tmoney.setText( StringUtil.endTwoChar(allCosts));

                    } else {
                        T.ShowToast(mContext, "获取未付费订单失败" + resOrder.getMessage(), 0);
                    }
                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。"+ex.getCause().getMessage(), 0);
                prgDialog.closeDialog();
            }
        });

    }

    private void initData() {
        cpa_toolbar.setTitle("");
        setSupportActionBar(cpa_toolbar);
        cpa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        cpa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        cpa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置recycelerView的布局管理器
        cpa_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        cpa_recyclerView.addItemDecoration(new DividerItemDecoration(this));

    }

    @OnClick({R.id.pca_iv_all_list, R.id.pca_btn_next})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pca_iv_all_list:
                ProjectApplication.intentManager.toHistoryCheckPaymentActivity(null);
                break;
            case R.id.pca_btn_next:
                Bundle bundle=new Bundle();
                if(allCosts==-1||allCosts==0){
                    T.ShowToast(mContext,"价格有误，无法支付");
                    return;
                }
                String data="{\"his_order\":\""+orderId+"\"}";
                bundle.putString("data",data);//订单ID
                bundle.putString("amount",""+allCount);//数量
                bundle.putString("price",StringUtil.endTwoChar(allCosts));//价格
//                bundle.putString("price","0.01");//价格
//                bundle.putString("price",""+sumcosts);//价格
                bundle.putString("name","缴费支付");//名称

                ProjectApplication.intentManager.toBillPaymentActivity(bundle);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
