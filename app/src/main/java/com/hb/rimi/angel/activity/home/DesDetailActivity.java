package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.util.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * B超更多详情....
 */
public class DesDetailActivity extends AppCompatActivity {
    @Bind(R.id.dda_tv_detail)
    TextView dda_tv_detail;
    String detail = "";
    String title = "";
    @Bind(R.id.desa_tv_title)
    TextView desaTvTitle;
    @Bind(R.id.desa_toolbar)
    Toolbar desaToolbar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_des_detail);
        ButterKnife.bind(this);
        initIntent();
        initData();
    }

    private void initData() {
        mContext = DesDetailActivity.this;
        desaToolbar.setTitle("");
        setSupportActionBar(desaToolbar);
        desaToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        desaToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        desaToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (StringUtil.isNotBlank(title)) {
            desaTvTitle.setText(title);
        }
        dda_tv_detail.setText(detail);
    }

    private void initIntent() {
        try {
            detail = getIntent().getExtras().getString("detail");
            title = getIntent().getExtras().getString("title");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
