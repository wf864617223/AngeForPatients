package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.MedicalVoucherAdapter;
import com.hb.rimi.angel.bean.ResMedicalVoucherInfo;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 就诊凭证+
 */
public class MedicalVoucherActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.mva_toolbar)
    Toolbar mva_toolbar;
    @Bind(R.id.mva_recyclerView)
    RecyclerView mva_recyclerView;
    @Bind(R.id.mva_ll_home)
    LinearLayout mva_ll_home;
    @Bind(R.id.order_list_refresh)
    SwipeRefreshLayout orderListRefresh;

    private MedicalVoucherAdapter adapter;
    private Context mContext;

    private int pageSize = 10;//每页显示数量
    private int pageNumber = 1;//页码

    private List<ResMedicalVoucherInfo.ResultBean.ListBean> medicalVoucherInfos = new ArrayList<>();
    private MedicalVoucherAdapter medicalVoucherAdapter;
    private int current = 1;
    private int total;
    private boolean isLoad = false;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_voucher);
        mContext = this;
        ButterKnife.bind(this);
        initData();
        initList();
    }

    private void initData() {
        mva_toolbar.setTitle("");
        setSupportActionBar(mva_toolbar);
        mva_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        mva_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mva_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置recycelerView的布局管理器
        manager = new LinearLayoutManager(this);
        mva_recyclerView.setLayoutManager(manager);
        //添加默认分割线
//        mva_recyclerView.addItemDecoration(new DividerItemDecoration(this));

        orderListRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        orderListRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                current = 1;
                if (medicalVoucherInfos != null && medicalVoucherInfos.size() > 0) {
                    medicalVoucherInfos.clear();
                }
                if (!isLoad) {
                    initList();
                    isLoad = true;
                }
            }
        });


        mva_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 2 >= medicalVoucherAdapter.getItemCount()) {
                    // TODO 加载更多
                    if (medicalVoucherInfos.size() < total) {
                        orderListRefresh.setRefreshing(true);
                        current++;
                        if (!isLoad) {
                            initList();
                            isLoad = true;
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = manager.findLastVisibleItemPosition();
            }
        });
    }

    private void initList() {
        if (!isLoad) {
            UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
            String name = resultBean.getName();
            if ("".equals(name)) {
                T.ShowToast(mContext, "新用户暂无此信息");
                return;
            }
            //请求数据
            String paramsUrl = "?pageSize=" + pageSize + "&pageNumber=" + current + "&status=1&types=1&types=2&types=3&token=" + ShareInfoUtil.readToken(mContext);
            System.out.println(HttpContanst.MEDICAL_VOUCHER + paramsUrl);
            HttpUtil.doHttp(HttpContanst.MEDICAL_VOUCHER + paramsUrl, null, new HttpUtil.IHttpResult() {
                @Override
                public void onSuccess(String result) {

                    if (StringUtil.isNotBlank(result)) {
                        ResMedicalVoucherInfo resBean = GsonTools.getBean(result, ResMedicalVoucherInfo.class);
                        if (resBean.getStatus() == 0) {

                            ResMedicalVoucherInfo.ResultBean resultBean = resBean.getResult();
                            isLoad = false;
                            total = resultBean.getTotalCount();
                            orderListRefresh.setRefreshing(false);
                            if (resBean.getResult().getList() != null) {
//                                medicalVoucherInfos=resBean.getResult().getList();
                                medicalVoucherInfos.addAll(resBean.getResult().getList());
                            }
                            total = resBean.getResult().getTotalCount();
                            //适配数据
                            medicalVoucherAdapter = new MedicalVoucherAdapter(mContext, medicalVoucherInfos);
                            mva_recyclerView.setAdapter(medicalVoucherAdapter);
                            medicalVoucherAdapter.notifyDataSetChanged();
                        } else {
                            T.ShowToast(mContext, "获取就诊凭证列表失败", 0);
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
            isLoad = true;
        }
    }

    @OnClick({R.id.mva_ll_home})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mva_ll_home:
                finish();
                break;
        }
    }
}
