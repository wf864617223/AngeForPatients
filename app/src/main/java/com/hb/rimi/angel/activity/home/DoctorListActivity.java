package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.DoctorListAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.Doctor;
import com.hb.rimi.angel.bean.ResDoctor;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 医生列表
 */
public class DoctorListActivity extends AppCompatActivity {
    @Bind(R.id.gla_recyclerView)
    RecyclerView gla_recyclerView;
    @Bind(R.id.gla_toolbar)
    Toolbar gla_toolbar;
    @Bind(R.id.dla_tv_title)
    TextView dla_tv_title;
    @Bind(R.id.dla_ll_home)
    LinearLayout dlaLlHome;
    private String id, name;

    private DoctorListAdapter gyenAdapter;
    private List<Doctor> doctors = null;
    private Context mContext = null;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_doctor_list);
        ButterKnife.bind(this);
        initIntent();
        initData();
        initList();


    }

    private void initIntent() {
        id = getIntent().getExtras().getString("id");

        name = getIntent().getExtras().getString("name");
//        if ("儿保科".equals(name)) {
//            name = "儿科";
//        }
    }

    private List<Doctor> initList() {
        prgDialog = new PrgDialog(mContext);
        //此处接口必须为POST
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "GetList");
        params.put("subject", name);
        HttpUtil.doHttp(HttpContanst.DEPARTYMENT_DOCTOR_LIST_NEW, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResDoctor resBean = GsonTools.getBean(result, ResDoctor.class);
                    if (resBean.getStatus() == 0) {
                        doctors = resBean.getResult();
                        //适配数据
                        gyenAdapter = new DoctorListAdapter(mContext, doctors, id, name);
                        gla_recyclerView.setAdapter(gyenAdapter);
                        gyenAdapter.notifyDataSetChanged();
                    } else {
                        T.ShowToast(mContext, "获取医生列表失败" + resBean.getMessage(), 0);
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

        return doctors;

    }

    private void initData() {
        mContext = DoctorListActivity.this;

        dla_tv_title.setText(name + "医生");
        gla_toolbar.setTitle("");
        setSupportActionBar(gla_toolbar);
        gla_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        gla_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        gla_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置recycelerView的布局管理器
        gla_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        gla_recyclerView.addItemDecoration(new DividerItemDecoration(this));
        dlaLlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectApplication.getInstanceApp().exit();
            }
        });
    }
}
