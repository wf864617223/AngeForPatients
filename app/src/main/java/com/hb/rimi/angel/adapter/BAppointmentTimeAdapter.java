package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.BAppointmentPayActivity;
import com.hb.rimi.angel.bean.BAppointmentTime;
import com.hb.rimi.angel.util.DateUtils;
import com.hb.rimi.angel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rimi on 2016/6/16.
 */

public class BAppointmentTimeAdapter extends RecyclerView.Adapter<BAppointmentTimeAdapter.MyViewHolder> {
    public List<RelativeLayout> rsList = new ArrayList<>();
    boolean isClick = false;
    private List<BAppointmentTime> bAppionmentTimes;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public BAppointmentTimeAdapter(Context mContext, List<BAppointmentTime> bAppionmentTimes) {
        this.mContext = mContext;
        this.bAppionmentTimes = bAppionmentTimes;
        layoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public BAppointmentTimeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_bapp_time, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BAppointmentTimeAdapter.MyViewHolder holder, final int position) {
        //TODO      holder.ipt_iv_status
        String date = bAppionmentTimes.get(position).getDate();
        if (StringUtil.isNotBlank(date) && date.length() > 10) {
            date = date.substring(0, 10);
        }
        holder.ibt_tv_date.setText(date);
        holder.ibt_tv_week.setText(DateUtils.getWeekOfDate(bAppionmentTimes.get(position).getDate()));
        rsList.add(holder.ibt_rl);
        holder.ibt_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = !isClick;
                if (isClick) {
                    for (int i = 0; i < rsList.size(); i++) {
                        rsList.get(i).setBackgroundResource(R.drawable.shape_round_ibt);
                    }
                    v.setBackgroundResource(R.drawable.icon_ibt_bg_focus);
                    BAppointmentPayActivity.baa_et_input_date.setText(bAppionmentTimes.get(position).getDate());
                    BAppointmentPayActivity.orderDate = bAppionmentTimes.get(position).getDate();
                } else {
                    for (int i = 0; i < rsList.size(); i++) {
                        rsList.get(i).setBackgroundResource(R.drawable.shape_round_ibt);
                    }
                    v.setBackgroundResource(R.drawable.shape_round_ibt);
                    BAppointmentPayActivity.baa_et_input_date.setText("");
                    BAppointmentPayActivity.orderDate = "";
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bAppionmentTimes == null ? 0 : bAppionmentTimes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ibt_tv_week, ibt_tv_date;
        RelativeLayout ibt_rl;

        public MyViewHolder(View itemView) {
            super(itemView);
            ibt_tv_week = (TextView) itemView.findViewById(R.id.ibt_tv_week);
            ibt_tv_date = (TextView) itemView.findViewById(R.id.ipt_tv_date);
            ibt_rl = (RelativeLayout) itemView.findViewById(R.id.ibt_rl);
        }
    }
}
