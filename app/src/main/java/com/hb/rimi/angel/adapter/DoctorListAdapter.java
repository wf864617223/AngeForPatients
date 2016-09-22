package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpLoadImg;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.Doctor;

import java.util.List;

/**
 * Created by rimi on 2016/6/1.
 */
public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder> {
    private Context mContext;
    private List<Doctor> doctors;
    private LayoutInflater layoutInflater;
    private String dptId;//科室ID
    private String dptName;//科室名称

    public DoctorListAdapter(Context mContext, List<Doctor> doctors, String dptId, String dptName) {
        this.mContext = mContext;
        this.doctors = doctors;
        this.dptName=dptName;
        this.dptId=dptId;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_doctor_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.ig_tv_name.setText(doctors.get(position).getName());
        holder.ig_tv_position.setText(doctors.get(position).getPosition());

        //圆角头像
        HttpLoadImg.loadCircleImg(mContext, doctors.get(position).getUrl(), holder.ig_iv_docimg);
        holder.ig_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("code", doctors.get(position).getCode());
                bundle.putString("name", doctors.get(position).getName());
                bundle.putString("url", doctors.get(position).getUrl());
                bundle.putString("position", doctors.get(position).getPosition());
                bundle.putString("dptId", dptId);
                bundle.putString("dptName", dptName);
                ProjectApplication.intentManager.toDoctorDetailActivity(bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctors == null ? 0 : doctors.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ig_tv_name;
        ImageView ig_iv_docimg;
        TextView ig_tv_position;
        RelativeLayout ig_rl;

        public MyViewHolder(View itemView) {
            super(itemView);
            ig_tv_name = (TextView) itemView.findViewById(R.id.ig_tv_name);
            ig_iv_docimg = (ImageView) itemView.findViewById(R.id.ig_iv_docimg);
            ig_tv_position = (TextView) itemView.findViewById(R.id.ig_tv_position);
            ig_rl = (RelativeLayout) itemView.findViewById(R.id.ig_rl);
        }
    }
}
