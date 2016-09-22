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
import com.hb.rimi.angel.adapter.BOptionalCheckAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.OptionalCheck;
import com.hb.rimi.angel.bean.ResOptionalCheck;
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
 * B超预约检查项列表
 */
public class BOptionalCheckActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.oca_toolbar)
    Toolbar oca_toolbar;
    @Bind(R.id.oca_recyclerView)
    RecyclerView oca_recyclerView;
    List<OptionalCheck> optionals = null;
    @Bind(R.id.oca_ll_call)
    LinearLayout ocaLlCall;
    private Context mContext;
    private PrgDialog prgDialog;
    private BOptionalCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_optional_check);
        ButterKnife.bind(this);
        initData();
        initList();

    }

    private void initData() {
        oca_toolbar.setTitle("");
        setSupportActionBar(oca_toolbar);
        oca_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        oca_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        oca_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置recycelerView的布局管理器
        oca_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        oca_recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }

    private void initList() {
        mContext = BOptionalCheckActivity.this;
        prgDialog = new PrgDialog(mContext);
        HttpUtil.doHttp(HttpContanst.OPTIONAL_CHECK_ITEM, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                if (StringUtil.isNotBlank(result)) {
                    ResOptionalCheck resBean = GsonTools.getBean(result, ResOptionalCheck.class);
                    if (resBean.getStatus() == 0) {
                        optionals = resBean.getResult();
                        //适配数据
                        adapter = new BOptionalCheckAdapter(mContext, optionals);
                        oca_recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        T.ShowToast(mContext, "获取检查项信息失败 " + resBean.getMessage(), 0);
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

    @OnClick({R.id.oca_ll_call})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oca_ll_call:
                CallManager.callPhone(this, ShareInfoUtil.readPhone(this));
                break;
        }
    }
}
