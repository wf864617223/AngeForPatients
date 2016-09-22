package com.hb.rimi.angel.activity.home;
/**
 * 就诊提醒页面
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.MedicalReminderAdapter;
import com.hb.rimi.angel.bean.MedicalReminder;
import com.hb.rimi.angel.bean.UserInfo;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.IntentManager;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MedicalReminderActivity extends AppCompatActivity {
    @Bind(R.id.mra_toolbar)
    Toolbar mra_toolbar;
    @Bind(R.id.mra_recyclerView)
    RecyclerView mra_recyclerView;
    @Bind(R.id.iv_kefu_phone)
    ImageView ivKefuPhone;
    private Context mContext;
    private MedicalReminderAdapter adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_reminder);
        ButterKnife.bind(this);
        initData();
        initList();

    }

    private void initData() {
        mContext = MedicalReminderActivity.this;
        mra_toolbar.setTitle("");
        setSupportActionBar(mra_toolbar);
        mra_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        mra_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mra_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置recycelerView的布局管理器
        mra_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        mra_recyclerView.addItemDecoration(new DividerItemDecoration(this));
        ivKefuPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentManager.getInstance(mContext).toMainActivity(null);
                //CallManager.callPhone(MedicalReminderActivity.this, ShareInfoUtil.readPhone(MedicalReminderActivity.this));
            }
        });

    }

    private void initList() {
        UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(mContext);
        String name = resultBean.getName();
        if("".equals(name)){
            T.ShowToast(mContext,"新用户暂无此信息");
            return;
        }
        List<MedicalReminder> reminderList = new ArrayList<MedicalReminder>();
        MedicalReminder medicalReminder = new MedicalReminder();
        medicalReminder.setType("1");
        medicalReminder.setName("会员预约信息");
        MedicalReminder medicalReminder2 = new MedicalReminder();
        medicalReminder2.setType("2");
        medicalReminder2.setName("预约挂号信息");
        MedicalReminder medicalReminder3 = new MedicalReminder();
        medicalReminder3.setType("3");
        medicalReminder3.setName("胎监检查信息");
        reminderList.add(medicalReminder);
        reminderList.add(medicalReminder2);
        reminderList.add(medicalReminder3);

        adapater = new MedicalReminderAdapter(mContext, reminderList);
        mra_recyclerView.setAdapter(adapater);
        adapater.notifyDataSetChanged();
    }
}
