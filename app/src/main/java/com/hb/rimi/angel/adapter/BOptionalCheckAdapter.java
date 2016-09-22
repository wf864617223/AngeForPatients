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
import com.hb.rimi.angel.bean.OptionalCheck;

import java.util.List;

/**
 *
 * Created by rimi on 2016/6/1.
 */
public class BOptionalCheckAdapter extends RecyclerView.Adapter<BOptionalCheckAdapter.MyViewHolder> {
    private Context mContext;
    private List<OptionalCheck> optionalChecks;
    private LayoutInflater layoutInflater;

    public BOptionalCheckAdapter(Context mContext, List<OptionalCheck> optionalChecks) {
        this.mContext = mContext;
        this.optionalChecks = optionalChecks;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_oprational_check, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.ioc_tv_name.setText(optionalChecks.get(position).getITEM_NAME());
        holder.ioc_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("targetType","0");
                bundle.putString("item_code",optionalChecks.get(position).getITEM_CODE());
                bundle.putDouble("item_id",optionalChecks.get(position).getITEM_ID());
                bundle.putString("item_name",optionalChecks.get(position).getITEM_NAME());
                bundle.putDouble("item_price",optionalChecks.get(position).getITEM_PRICE());
                bundle.putString("describe",optionalChecks.get(position).getDESCRIBE());
                bundle.putString("describe_detail",optionalChecks.get(position).getDESCRIBE_DETAIL());
//                if(!VocationalUtil.hasIcNo(mContext)||!VocationalUtil.hasMenses(mContext)){
//                    ProjectApplication.intentManager.toAddUserInfoActivity(bundle);
//                }else{
                    ProjectApplication.intentManager.toBAppointmentPayActivity(bundle);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionalChecks == null ? 0 : optionalChecks.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ioc_tv_name;
        RelativeLayout ioc_rl;

        public MyViewHolder(View itemView) {
            super(itemView);
            ioc_tv_name = (TextView) itemView.findViewById(R.id.ioc_tv_name);
            ioc_rl = (RelativeLayout) itemView.findViewById(R.id.ioc_rl);
        }
    }
}
