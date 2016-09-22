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
import com.hb.rimi.angel.bean.Department;

import java.util.List;

/**
 * Created by rimi on 2016/6/1.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {
    private Context mContext;
    private List<Department> departments;
    private LayoutInflater layoutInflater;

    public AppointmentAdapter(Context mContext, List<Department> departments) {
        this.mContext = mContext;
        this.departments = departments;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_appointment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.ia_tv_department.setText(departments.get(position).getNAME());
        holder.ia_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("id",departments.get(position).getID());
                bundle.putString("name",departments.get(position).getNAME());
                ProjectApplication.intentManager.toDoctorListListActivity(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return departments == null ? 0 : departments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ia_tv_department;
        RelativeLayout ia_rl;

        public MyViewHolder(View itemView) {
            super(itemView);
            ia_tv_department = (TextView) itemView.findViewById(R.id.ia_tv_department);
            ia_rl = (RelativeLayout) itemView.findViewById(R.id.ia_rl);
        }
    }
}
