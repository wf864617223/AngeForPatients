package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.CheckPaymentAdapter;
import com.hb.rimi.angel.bean.Order;
import com.hb.rimi.angel.bean.ResOrder;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 历史检查缴费
 */
public class HistoryCheckPaymentActivity extends AppCompatActivity {
    @Bind(R.id.hcpa_recyclerView)
    RecyclerView hcpa_recyclerView;
    @Bind(R.id.hcpa_toolbar)
    Toolbar hcpa_toolbar;
    private List<Order> orders = new ArrayList<Order>();
    private Context mContext;
    private CheckPaymentAdapter adapter;
    private PrgDialog prgDialog;
    private String pre_status = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_check_payment);
        ButterKnife.bind(this);
        initData();
        initList();
    }

    private void initData() {
        mContext = HistoryCheckPaymentActivity.this;
        hcpa_toolbar.setTitle("");
        setSupportActionBar(hcpa_toolbar);
        hcpa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        hcpa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        hcpa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置recycelerView的布局管理器
        hcpa_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        hcpa_recyclerView.addItemDecoration(new DividerItemDecoration(this));

    }

    private void initList() {
        prgDialog = new PrgDialog(mContext);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "nopaid");
//  TODO 写死ICNO      params.put("ic_no", ShareInfoUtil.readResultBean(mContext).getIC_NO());
//        params.put("day_date", new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(new Date()));
        params.put("day_date", "2016-06-15 00:00:00");
        params.put("ic_no", "a1606150011");
        params.put("pre_status", pre_status);
        params.put("token", ShareInfoUtil.readToken(mContext));
        HttpUtil.doHttp(HttpContanst.ALL_CONSUMER_PRESCRIPTIONS_LIST, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResOrder resOrder = GsonTools.getBean(result, ResOrder.class);
                    if (resOrder.getStatus() == 0) {
                        orders = resOrder.getResult();
                        //适配数据
                        if (adapter == null) {
                            adapter = new CheckPaymentAdapter(mContext, orders);
                            hcpa_recyclerView.setAdapter(adapter);
                        }
                        adapter.notifyDataSetChanged();

                    } else {
                        T.ShowToast(mContext, "获取所有消费处方列表失败 " + resOrder.getMessage(), 0);
                    }
                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                prgDialog.closeDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
