package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.MedicalReminder;

import java.util.List;

/**
 * Created by rimi on 2016/6/1.
 */
public class MedicalReminderAdapter extends RecyclerView.Adapter<MedicalReminderAdapter.MyViewHolder> {
    private Context mContext;
    private List<MedicalReminder> medicalReminders;
    private LayoutInflater layoutInflater;

    public MedicalReminderAdapter(Context mContext, List<MedicalReminder> medicalReminders) {
        this.mContext = mContext;
        this.medicalReminders = medicalReminders;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_medical_reminder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imr_tv_name.setText(medicalReminders.get(position).getName());
        holder.imr_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("title",medicalReminders.get(position).getName());
                ProjectApplication.intentManager.toVisitRemindActivity(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicalReminders == null ? 0 : medicalReminders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imr_tv_name;
        RelativeLayout imr_rl;

        public MyViewHolder(View itemView) {
            super(itemView);
            imr_tv_name = (TextView) itemView.findViewById(R.id.imr_tv_name);
            imr_rl = (RelativeLayout) itemView.findViewById(R.id.imr_rl);
        }
    }
}
