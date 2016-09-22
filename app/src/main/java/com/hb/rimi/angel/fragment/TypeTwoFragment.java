package com.hb.rimi.angel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpUtil;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.WatchPicActivity;
import com.hb.rimi.angel.adapter.TypePicsAdapter;
import com.hb.rimi.angel.bean.ReportPacs;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.util.GsonTools;
import com.hb.rimi.angel.util.MyListView;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.T;
import com.hb.rimi.angel.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/13.
 */
@ContentView(R.layout.frag_type_pause)
public class TypeTwoFragment extends Fragment {


    @Bind(R.id.lv_report_img)
    MyListView lvReportImg;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tvItemResult)
    TextView tvItemResult;
    @Bind(R.id.tvItemResult1)
    TextView tvItemResult1;
    @Bind(R.id.tv_dept)
    TextView tvDept;
    @Bind(R.id.tv_bedno)
    TextView tvBedno;
    @Bind(R.id.tv_bir)
    TextView tvBir;
    @Bind(R.id.tv_techNo)
    TextView tvTechNo;
    @Bind(R.id.tv_cardNo)
    TextView tvCardNo;
    @Bind(R.id.tv_checkBody)
    TextView tvCheckBody;
    @Bind(R.id.tv_ReportDoctorName)
    TextView tvReportDoctorName;
    @Bind(R.id.tv_ReportWriterName)
    TextView tvReportWriterName;
    @Bind(R.id.tv_ExecTime)
    TextView tvExecTime;
    @Bind(R.id.tv_Instrument)
    TextView tvInstrument;
    @Bind(R.id.iv_user_img)
    ImageView ivUserImg;
    @Bind(R.id.ll_watch_pic)
    LinearLayout llWatchPic;
    private List<ReportPacs.ResultBean.ListBean> list = new ArrayList<>();
    private TypePicsAdapter adapter;
    private PrgDialog prgDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        final Bundle bundle = getArguments();
        final String id = bundle.getString("id");
        prgDialog = new PrgDialog(getContext());
        HashMap<String, String> parms = new HashMap<>();
        parms.put("type", "2");
        parms.put("id", id);
        HttpUtil.doHttp(HttpContanst.REPORT_INFO, parms, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                ReportPacs reportPacs = GsonTools.getBean(result, ReportPacs.class);
                int status = reportPacs.getStatus();
                if (status == 0) {
                    ReportPacs.ResultBean result1 = reportPacs.getResult();
                    tvName.setText("就诊人：" + result1.getPatName());
                    tvTechNo.setText("检查号：" + result1.getTechNo());
                    tvCheckBody.setText("检查部位：" + result1.getExamination());
                    tvInstrument.setText("检查设备：" + result1.getInstrument());
                    int sex = result1.getSex();
                    if (sex == 1) {
                        tvSex.setText("性别：男");
                        ivUserImg.setImageResource(R.mipmap.man);
                    } else {
                        tvSex.setText("性别：女");
                        ivUserImg.setImageResource(R.mipmap.woman);
                    }
                    //tvSex.setText(result1.getSex()+"");
                    tvAge.setText("年龄：" + result1.getAge());
                    tvCardNo.setText("门诊号：" + result1.getCardNo());
                    tvDept.setText("科室：" + result1.getApplyDeptName());
                    tvBedno.setText("床号：" + result1.getBedNo());
                    String itemResult = result1.getItemResult();
                    String str = null;
                    try {
                        str = new String(Base64.decode(itemResult.getBytes(), Base64.DEFAULT), "gb2312");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    tvItemResult.setText("超声提示：" + str);
                    tvReportDoctorName.setText("检查医生：" + result1.getReportDoctorName());
                    tvReportWriterName.setText("报告录入：" + result1.getReportWriterName());
                    //tvReportTime.setText("报告时间：" + result1.getReportTime());
                    String execTime = result1.getExecTime();
                    String[] split = execTime.split(" ");
                    if (split[1].equals("0:00:00")) {
                        tvExecTime.setText("检查时间：" + split[0]);
                    } else {
                        tvExecTime.setText("检查时间：" + result1.getExecTime());
                    }

                    String itemResult1 = result1.getItemResult1();
                    byte[] decode = Base64.decode(itemResult1.getBytes(), Base64.DEFAULT);
                    String s = null;
                    try {
                        s = new String(decode, "gb2312");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    tvItemResult1.setText("超声描述：" + s);

                    list = result1.getList();
                    //ShareInfoUtil.saveImgList(getContext(),list);
                    //adapter = new TypePicsAdapter(list, getContext());
                    //lvReportImg.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                    prgDialog.closeDialog();
                } else {
                    T.ShowToast(getContext(), "数据出错，请稍后再试");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.ShowToast(getContext(), "请求失败，请重试");
            }
        });
        llWatchPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WatchPicActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("id",id);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
