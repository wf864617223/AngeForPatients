package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.AppointmentAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.Department;
import com.hb.rimi.angel.bean.ResDepartment;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约挂号
 */
public class AppointmentActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.aa_toolbar)
    Toolbar aa_toolbar;
    @Bind(R.id.aa_recyclerView)
    RecyclerView aa_recyclerView;
    @Bind(R.id.aa_ll_call)
    LinearLayout aa_ll_call;
    List<Department> departments = null;
    private AppointmentAdapter appointmentAdpater;
    private Context mContext;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        initData();
        initList();
    }

    private void initList() {
        mContext = AppointmentActivity.this;
        prgDialog = new PrgDialog(mContext);
        HttpUtil.doHttp(HttpContanst.DEPARTYMENT_LIST, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResDepartment resBean = GsonTools.getBean(result, ResDepartment.class);
                    if (resBean.getStatus() == 0) {
                        departments = resBean.getResult();
                        //适配数据
                        appointmentAdpater = new AppointmentAdapter(mContext, departments);
                        aa_recyclerView.setAdapter(appointmentAdpater);
                        appointmentAdpater.notifyDataSetChanged();
                    } else {
                        T.ShowToast(mContext, "获取科室信息失败" + resBean.getMessage(), 0);
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

    private void initData() {
        aa_toolbar.setTitle("");
        setSupportActionBar(aa_toolbar);
        aa_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        aa_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        aa_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置recycelerView的布局管理器
        aa_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        aa_recyclerView.addItemDecoration(new DividerItemDecoration(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.aa_ll_call})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.aa_ll_call:
                CallManager.callPhone(this, ShareInfoUtil.readPhone(this));
                break;
        }
    }
}
