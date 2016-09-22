package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.AppionmentTime;
import com.hb.rimi.angel.bean.DoctorDetail;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.StringUtil;

import java.util.List;

/**
 * 预约或已满
 * Created by rimi on 2016/6/6.
 */
public class AppointmentTimeAdapter extends RecyclerView.Adapter<AppointmentTimeAdapter.MyViewHolder> {
    private List<AppionmentTime> appTimes;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private DoctorDetail doctorDetail;
    private String dptId;//科室ID
    private String dptName;//科室名称

    public AppointmentTimeAdapter(Context mContext, List<AppionmentTime> appTimes, DoctorDetail doctorDetail, String dptId, String dptName) {
        this.mContext = mContext;
        this.appTimes = appTimes;
        this.doctorDetail = doctorDetail;
        this.dptId = dptId;
        this.dptName = dptName;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public AppointmentTimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_app_time, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentTimeAdapter.MyViewHolder holder, final int position) {
//TODO      holder.ipt_iv_status
//格式化时间为日期
        String idate = appTimes.get(position).getDate();
        if (StringUtil.isNotBlank(idate) && idate.length() > 8) {
            idate = idate.substring(0, 10);
        }
        holder.ipt_tv_date.setText(idate);
        holder.ipt_tv_week.setText(DateUtils.getWeekOfDate(appTimes.get(position).getDate()));


        if (appTimes.get(position).getCount() - appTimes.get(position).getSuccessCount() > 0) {
            holder.ipt_btn_status.setText("预约");
        } else {
            holder.ipt_btn_status.setText("已满");
            holder.ipt_btn_status.setBackgroundColor(Color.RED);
        }
        holder.ipt_btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("targetType", "1");
                bundle.putString("id", appTimes.get(position).getID());
                bundle.putString("name", appTimes.get(position).getDoctorName());
                bundle.putString("code", appTimes.get(position).getDoctorCode());
                bundle.putString("url", doctorDetail.getUrl());
                bundle.putString("position", doctorDetail.getPosition());
                bundle.putString("dptId", dptId);
                bundle.putString("dptName", dptName);
                //格式化时间为日期
                String date = appTimes.get(position).getDate();
                if (StringUtil.isNotBlank(date) && date.length() > 8) {
                    date = date.substring(0, 10);
                }

                bundle.putString("date", date);
                bundle.putString("week", DateUtils.getWeekOfDate(appTimes.get(position).getDate()));
                if (appTimes.get(position).getCount() - appTimes.get(position).getSuccessCount() > 0) {
                    /*if (!VocationalUtil.hasIcNo(mContext)) {
                        ProjectApplication.intentManager.toAddUserInfoActivity(bundle);
                    } else {*/
                    ProjectApplication.intentManager.toAppointmentPayActivity(bundle);

                    //}
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appTimes == null ? 0 : appTimes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ipt_tv_date;
        TextView ipt_tv_week;
        Button ipt_btn_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            ipt_tv_date = (TextView) itemView.findViewById(R.id.ipt_tv_date);
            ipt_tv_week = (TextView) itemView.findViewById(R.id.ipt_tv_week);
            ipt_btn_status = (Button) itemView.findViewById(R.id.ipt_btn_status);
        }
    }
}
