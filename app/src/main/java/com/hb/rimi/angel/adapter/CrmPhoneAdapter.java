package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.ResCrmPhone;

import java.util.List;

/**
 * 电话挂号列表适配器
 * Created by rimi on 2016/6/1.
 */
public class CrmPhoneAdapter extends RecyclerView.Adapter<CrmPhoneAdapter.MyViewHolder> {
    private Context mContext;
    private List<ResCrmPhone.ResultBean> resCrmPhoneList;
    private LayoutInflater layoutInflater;

    public CrmPhoneAdapter(Context mContext, List<ResCrmPhone.ResultBean> resCrmPhoneList) {
        this.mContext = mContext;
        this.resCrmPhoneList = resCrmPhoneList;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_crm_phone, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.icp_tv_costs.setText("" + resCrmPhoneList.get(position).getPrice());
        holder.icp_tv_date_created.setText(resCrmPhoneList.get(position).getDateCreated());

        if (resCrmPhoneList.get(position).getDateEnd() != null && resCrmPhoneList.get(position).getDateEnd().length() > 10) {
            holder.icp_tv_end_date.setText(resCrmPhoneList.get(position).getDateEnd().substring(0, 10));
        }
        holder.icp_tv_pro_name.setText(resCrmPhoneList.get(position).getProName());
        holder.icp_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 去支付
                Bundle bundle = new Bundle();
                bundle.putString("targetType", "0");
                try {
                    bundle.putDouble("item_id", Double.valueOf(resCrmPhoneList.get(position).getType()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                bundle.putString("item_name", resCrmPhoneList.get(position).getProName());
                bundle.putString("wdyy_id", ""+resCrmPhoneList.get(position).getID());
                bundle.putString("end_date", resCrmPhoneList.get(position).getDateEnd());
                bundle.putDouble("item_price", resCrmPhoneList.get(position).getPrice());
                bundle.putString("describe", resCrmPhoneList.get(position).getSimpleDsc());
                bundle.putString("describe_detail", resCrmPhoneList.get(position).getDetailDsc());
                ProjectApplication.intentManager.toBAppointmentPayActivity(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resCrmPhoneList == null ? 0 : resCrmPhoneList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView icp_tv_costs,
                icp_tv_date_created,
                icp_tv_end_date,
                icp_tv_pro_name;
        LinearLayout icp_ll;

        public MyViewHolder(View itemView) {
            super(itemView);

            icp_tv_costs = (TextView) itemView.findViewById(R.id.icp_tv_costs);
            icp_tv_date_created = (TextView) itemView.findViewById(R.id.icp_tv_date_created);
            icp_tv_end_date = (TextView) itemView.findViewById(R.id.icp_tv_end_date);
            icp_tv_pro_name = (TextView) itemView.findViewById(R.id.icp_tv_pro_name);

            icp_ll = (LinearLayout) itemView.findViewById(R.id.icp_ll);
        }
    }
}
