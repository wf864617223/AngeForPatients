package com.hb.rimi.angel.activity.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpLoadImg;
import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.adapter.AppointmentTimeAdapter;
import com.hb.rimi.angel.adapter.DoctorListAdapter;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.AppionmentTime;
import com.hb.rimi.angel.bean.Doctor;
import com.hb.rimi.angel.bean.DoctorDetail;
import com.hb.rimi.angel.bean.ResAppointmentTime;
import com.hb.rimi.angel.bean.ResDoctorDetail;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.CallManager;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.DividerItemDecoration;
import com.hb.rimi.angel.view.PrgDialog;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医生详情
 */
public class DoctorDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.da_toolbar)
    Toolbar da_toolbar;
    @Bind(R.id.dda_iv_docimg)
    ImageView dda_iv_docimg;
    @Bind(R.id.dda_tv_name)
    TextView dda_tv_name;
    @Bind(R.id.dda_tv_position)
    TextView dda_tv_position;
    @Bind(R.id.dda_recyclerView)
    RecyclerView dda_recyclerView;
    //控制与隐藏

    @Bind(R.id.dda_ll_individual_from)
    LinearLayout dda_ll_individual_from;
    @Bind(R.id.dda_tv_individual_from)
    TextView dda_tv_individual_from;

    @Bind(R.id.dda_ll_individual_resume)
    LinearLayout dda_ll_individual_resume;
    @Bind(R.id.dda_tv_individual_resume)
    TextView dda_tv_individual_resume;
    @Bind(R.id.dda_ll_professional_qualification)
    LinearLayout dda_ll_professional_qualification;
    @Bind(R.id.dda_tv_professional_qualification)
    TextView dda_tv_professional_qualification;
    @Bind(R.id.dda_ll_good_field)
    LinearLayout dda_ll_good_field;
    @Bind(R.id.dda_tv_good_field)
    TextView dda_tv_good_field;

    //展开与隐藏label
    @Bind(R.id.dda_rl_individual_from)
    RelativeLayout dda_rl_individual_from;
    @Bind(R.id.dda_rl_individual_resume)
    RelativeLayout dda_rl_individual_resume;
    @Bind(R.id.dda_rl_label_professional_qualification)
    RelativeLayout dda_rl_label_professional_qualification;
    @Bind(R.id.dda_rl_good_field)
    RelativeLayout dda_rl_good_field;

    @Bind(R.id.dda_ll_appoint_time)
    RelativeLayout dda_ll_appoint_time;
    @Bind(R.id.dda_rl_open)
    RelativeLayout dda_rl_open;

    @Bind(R.id.dda_iv_open)
    ImageView dda_iv_open;
    @Bind(R.id.dda_tv_open)
    TextView dda_tv_open;
    PrgDialog prgDialog;
    List<AppionmentTime> appionmentTimes;
    @Bind(R.id.doctor_line_phone)
    ImageView doctorLinePhone;
    @Bind(R.id.dol_ll_home)
    LinearLayout dolLlHome;
    @Bind(R.id.add_iv_from)
    ImageView addIvFrom;
    //    @Bind(R.id.dda_rl_individual_from)
//    RelativeLayout ddaRlIndividualFrom;
//    @Bind(R.id.add_iv_resume)
//    ImageView addIvResume;
    @Bind(R.id.add_iv_professional_qualification)
    ImageView addIvProfessionalQualification;
    @Bind(R.id.add_iv_good_field)
    ImageView addIvGoodField;
    //    @Bind(R.id.dda_iv_time)
//    ImageView ddaIvTime;
    @Bind(R.id.dda_tv_apptime)
    TextView dda_tv_apptime;
    @Bind(R.id.dda_title_tv_individual_resume)
    TextView dda_title_tv_individual_resume;
    @Bind(R.id.dda_iv_time)
    ImageView dda_iv_time;
    @Bind(R.id.add_iv_resume)
    ImageView add_iv_resume;
    String instr = "";
    private Context mContext;
    private AppointmentTimeAdapter adapter;
    //获取上个页面传递的医生id 名称 图片 职位
    private String code;
    private String name;
    private String url;
    private String position;
    private String dptId;//科室Id
    private String dptName;//科室名称
    private List<Doctor> doctors;
    private DoctorListAdapter doctorListAdapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectApplication.getInstanceApp().addActivity(this);
        setContentView(R.layout.activity_doctor_detail);
        ButterKnife.bind(this);
        initIntent();
        initData();
        initList();
    }

    private void initIntent() {
        code = getIntent().getExtras().getString("code");
        name = getIntent().getExtras().getString("name");
        url = getIntent().getExtras().getString("url");
        position = getIntent().getExtras().getString("position");
        dptId = getIntent().getExtras().getString("dptId");
        dptName = getIntent().getExtras().getString("dptName");

    }

    private void initList() {
        mContext = DoctorDetailActivity.this;

        prgDialog = new PrgDialog(mContext);
        //获取医生详情
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("action", "GetDoctor");
        params.put("code", code);
        HttpUtil.doHttp(HttpContanst.DEPARTYMENT_DOCTOR_DETAIL, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {

                if (StringUtil.isNotBlank(result)) {

                    ResDoctorDetail resBean = GsonTools.getBean(result, ResDoctorDetail.class);
                    if (resBean.getStatus() == 0) {
                        final DoctorDetail doctorDetail = resBean.getResult();
                        //赋初始值
                        dda_tv_name.setText(doctorDetail.getName());
                        dda_tv_position.setText(doctorDetail.getPosition());
                        HttpLoadImg.loadCircleImg(mContext, doctorDetail.getUrl(), dda_iv_docimg);
                        dda_tv_individual_from.setText(doctorDetail.getCome());
                        instr = doctorDetail.getIntroduction();
                        dda_tv_individual_resume.setText(instr.substring(0, 80));

                        dda_tv_professional_qualification.setText(doctorDetail.getQualifications());
                        dda_tv_good_field.setText(doctorDetail.getSpecialty());

                        if (instr.length() > 80) {
                            dda_rl_open.setVisibility(View.VISIBLE);
                        } else {
                            dda_rl_open.setVisibility(View.GONE);
                        }
                        //获取医生一周内预约信息
                        HashMap<String, String> params2 = new HashMap<String, String>();
                        params2.put("action", "GetDateList");
                        params2.put("code", code);
                        HttpUtil.doHttp(HttpContanst.DOCTOR_WEEK, params2, new HttpUtil.IHttpResult() {
                            @Override
                            public void onSuccess(String result) {
                                if (StringUtil.isNotBlank(result)) {
                                    ResAppointmentTime resBean = GsonTools.getBean(result, ResAppointmentTime.class);
                                    if (resBean.getStatus() == 0) {
                                        appionmentTimes = resBean.getResult();
                                        //赋初始值
                                        adapter = new AppointmentTimeAdapter(mContext, appionmentTimes, doctorDetail, dptId, dptName);
                                        dda_recyclerView.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        T.ShowToast(mContext,"暂无医生预约信息", 0);
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
                    } else {
                        T.ShowToast(mContext, "暂无医生信息", 0);
                        dda_rl_open.setVisibility(View.GONE);
                    }
                } else {
                    T.ShowToast(mContext, "返回数据为空", 0);
                    dda_rl_open.setVisibility(View.GONE);
                }
                prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dda_rl_open.setVisibility(View.GONE);
                T.ShowToast(mContext, "请求失败，请稍后重试。", 0);
                prgDialog.closeDialog();

            }
        });


    }


    private void initData() {
        mContext = DoctorDetailActivity.this;
        da_toolbar.setTitle("");
        setSupportActionBar(da_toolbar);
        da_toolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        da_toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        da_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置recycelerView的布局管理器
        dda_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加默认分割线
        dda_recyclerView.addItemDecoration(new DividerItemDecoration(this));

        dolLlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectApplication.getInstanceApp().exit();
            }
        });
    }


    //展开与隐藏label
    @OnClick({R.id.dda_rl_open, R.id.dda_ll_appoint_time, R.id.dda_rl_individual_from, R.id.dda_rl_individual_resume, R.id.dda_rl_label_professional_qualification, R.id.dda_rl_good_field, R.id.doctor_line_phone})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.dda_rl_open:
                dda_tv_individual_resume.setVisibility(View.GONE);
                dda_iv_open.setVisibility(View.GONE);
                dda_tv_open.setText(instr);
                dda_rl_open.setBackgroundColor(Color.parseColor("#ffffff"));
                dda_tv_open.setBackgroundColor(Color.parseColor("#ffffff"));

                break;
            case R.id.dda_ll_appoint_time:
                showHideLayout(R.id.dda_ll_appoint_time);
                break;
            case R.id.dda_rl_individual_from:
                showHideLayout(R.id.dda_rl_individual_from);
                break;
            case R.id.dda_rl_individual_resume:
                showHideLayout(R.id.dda_rl_individual_resume);
                break;
            case R.id.dda_rl_label_professional_qualification:
                showHideLayout(R.id.dda_rl_label_professional_qualification);
                break;
            case R.id.dda_rl_good_field:
                showHideLayout(R.id.dda_rl_good_field);
                break;
            case R.id.doctor_line_phone:
                CallManager.callPhone(this, ShareInfoUtil.readPhone(this));
                break;
        }
    }


    public void showHideLayout(int resId) {
        switch (resId) {
            case R.id.dda_ll_appoint_time:

                if (dda_recyclerView.getVisibility() == View.VISIBLE) {
                    dda_recyclerView.setVisibility(View.GONE);
                    dda_iv_time.setImageResource(R.mipmap.icon_appointment_focus);
                    dda_tv_apptime.setTextColor(getResources().getColor(R.color.color_mva_person));
                    dda_ll_appoint_time.setBackgroundColor(getResources().getColor(R.color.white));

                } else {
                    dda_iv_time.setImageResource(R.mipmap.icon_appointment_normal);
                    dda_recyclerView.setVisibility(View.VISIBLE);
                    dda_ll_appoint_time.setBackgroundColor(getResources().getColor(R.color.color_blue_reg));
                    dda_tv_apptime.setTextColor(getResources().getColor(R.color.white));
                }
                break;
            case R.id.dda_rl_individual_from:
                if (dda_ll_individual_from.getVisibility() == View.VISIBLE) {
                    dda_ll_individual_from.setVisibility(View.GONE);
                } else {
                    dda_ll_individual_from.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dda_rl_individual_resume:
                if (dda_ll_individual_resume.getVisibility() == View.VISIBLE) {
                    add_iv_resume.setImageResource(R.mipmap.icon_individual_resume_focus);
                    dda_ll_individual_resume.setVisibility(View.GONE);
                    dda_title_tv_individual_resume.setTextColor(getResources().getColor(R.color.color_mva_person));
                    dda_rl_individual_resume.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    add_iv_resume.setImageResource(R.mipmap.icon_individual_resume_normal);
                    dda_ll_individual_resume.setVisibility(View.VISIBLE);
                    dda_title_tv_individual_resume.setTextColor(getResources().getColor(R.color.white));
                    dda_rl_individual_resume.setBackgroundColor(getResources().getColor(R.color.color_blue_reg));
                }
                break;
            case R.id.dda_rl_label_professional_qualification:
                if (dda_ll_professional_qualification.getVisibility() == View.VISIBLE) {
                    dda_ll_professional_qualification.setVisibility(View.GONE);
                } else {
                    dda_ll_professional_qualification.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.dda_rl_good_field:
                if (dda_ll_good_field.getVisibility() == View.VISIBLE) {
                    dda_ll_good_field.setVisibility(View.GONE);
                } else {
                    dda_ll_good_field.setVisibility(View.VISIBLE);
                }
                break;

        }
    }
}
