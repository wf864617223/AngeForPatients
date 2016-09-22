package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ResMedicalVoucherInfo;
import com.hb.rimi.angel.util.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by rimi on 2016/6/8.
 */
public class MedicalVoucherAdapter extends RecyclerView.Adapter<MedicalVoucherAdapter.MyViewHolder> {
    private Context mContext;
    private List<ResMedicalVoucherInfo.ResultBean.ListBean> medicalVoucherInfos;
    private LayoutInflater layoutInflater;

    public MedicalVoucherAdapter(Context mContext, List<ResMedicalVoucherInfo.ResultBean.ListBean> medicalVoucherInfos) {
        this.mContext = mContext;
        this.medicalVoucherInfos = medicalVoucherInfos;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MedicalVoucherAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_medical_voucher, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicalVoucherAdapter.MyViewHolder holder, int position) {


        holder.imv_tv_voucherType.setText(medicalVoucherInfos.get(position).getType());
        holder.imv_tv_order_name.setText(medicalVoucherInfos.get(position).getName());
        holder.imv_tv_voucherIcNo.setText(medicalVoucherInfos.get(position).getId());
        //商城订单：0
//        医生挂号：1
//        检查预约：2
//        门诊缴费：3

        if ("医生挂号".equals(medicalVoucherInfos.get(position).getType())) {

            holder.imv_ll_project_name.setVisibility(View.GONE);
            holder.imv_ll_voucherDepartment.setVisibility(View.VISIBLE);
            holder.imv_ll_doctor.setVisibility(View.VISIBLE);
            holder.imv_ll_app_time.setVisibility(View.VISIBLE);
            holder.imv_ll_note.setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObject = new JSONObject(medicalVoucherInfos.get(position).getData());
                if (jsonObject.has("department_name")) {
                    String department_name = jsonObject.getString("department_name");
                    holder.imv_tv_voucherDepartment.setText(department_name);
                }
                if (jsonObject.has("doctor_name")) {
                    String doctor_name = jsonObject.getString("doctor_name");
                    holder.imv_tv_doctor.setText(doctor_name);
                }
                if (jsonObject.has("note")) {
                    String note = jsonObject.getString("note");
                    holder.imv_tv_note.setText(note);
                }
                if (jsonObject.has("date")) {
                    String date = jsonObject.getString("date");
                    holder.imv_tv_date.setText(date);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if ("商城订单".equals(medicalVoucherInfos.get(position).getType())) {
            holder.imv_ll_project_name.setVisibility(View.GONE);
            holder.imv_ll_voucherDepartment.setVisibility(View.GONE);
            holder.imv_ll_doctor.setVisibility(View.GONE);
            holder.imv_ll_app_time.setVisibility(View.GONE);
            holder.imv_ll_note.setVisibility(View.GONE);
        }
        if ("检查预约".equals(medicalVoucherInfos.get(position).getType())) {
            holder.imv_ll_project_name.setVisibility(View.VISIBLE);
            holder.imv_ll_app_time.setVisibility(View.VISIBLE);
            holder.imv_ll_voucherDepartment.setVisibility(View.GONE);
            holder.imv_ll_doctor.setVisibility(View.GONE);
            holder.imv_ll_note.setVisibility(View.VISIBLE);
            try {
                JSONObject jsonObject = new JSONObject(medicalVoucherInfos.get(position).getData());
                if (jsonObject.has("appointment_name")) {
                    String department_name = jsonObject.getString("appointment_name");
                    holder.imv_tv_project_name.setText(department_name);
                }

                if (jsonObject.has("note")) {
                    String note = jsonObject.getString("note");
                    holder.imv_tv_note.setText(note);
                }
                if (jsonObject.has("date")) {
                    String date = jsonObject.getString("date");
                    holder.imv_tv_date.setText(date);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if ("门诊缴费".equals(medicalVoucherInfos.get(position).getType())) {
            holder.imv_ll_project_name.setVisibility(View.GONE);
            holder.imv_ll_voucherDepartment.setVisibility(View.GONE);
            holder.imv_ll_doctor.setVisibility(View.GONE);
            holder.imv_ll_app_time.setVisibility(View.GONE);
            holder.imv_ll_note.setVisibility(View.GONE);
        }

        holder.imv_tv_pay_time.setText(DateUtils.convert2String(medicalVoucherInfos.get(position).getPayOrder().getPayTime(), null));

    }

    @Override
    public int getItemCount() {
        return medicalVoucherInfos == null ? 0 : medicalVoucherInfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imv_tv_voucherType, imv_tv_order_name, imv_tv_voucherIcNo, imv_tv_voucherDepartment, imv_tv_doctor, imv_tv_date;
        TextView imv_tv_note, imv_tv_project_name, imv_tv_pay_time;
        LinearLayout imv_ll_project_name, imv_ll_voucherDepartment, imv_ll_doctor, imv_ll_app_time, imv_ll_note;

        public MyViewHolder(View itemView) {
            super(itemView);
            imv_tv_voucherType = (TextView) itemView.findViewById(R.id.imv_tv_voucherType);
            imv_tv_order_name = (TextView) itemView.findViewById(R.id.imv_tv_order_name);
            imv_tv_voucherIcNo = (TextView) itemView.findViewById(R.id.imv_tv_voucherIcNo);
            imv_tv_voucherDepartment = (TextView) itemView.findViewById(R.id.imv_tv_voucherDepartment);
            imv_tv_doctor = (TextView) itemView.findViewById(R.id.imv_tv_doctor);
            imv_tv_date = (TextView) itemView.findViewById(R.id.imv_tv_date);
            imv_tv_note = (TextView) itemView.findViewById(R.id.imv_tv_note);
            imv_tv_project_name = (TextView) itemView.findViewById(R.id.imv_tv_project_name);
            imv_tv_pay_time = (TextView) itemView.findViewById(R.id.imv_tv_pay_time);
            imv_ll_app_time = (LinearLayout) itemView.findViewById(R.id.imv_ll_app_time);

            imv_ll_project_name = (LinearLayout) itemView.findViewById(R.id.imv_ll_project_name);
            imv_ll_voucherDepartment = (LinearLayout) itemView.findViewById(R.id.imv_ll_voucherDepartment);
            imv_ll_doctor = (LinearLayout) itemView.findViewById(R.id.imv_ll_doctor);
            imv_ll_note = (LinearLayout) itemView.findViewById(R.id.imv_ll_note);

        }
    }
}

