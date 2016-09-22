package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.home.CheckPaymentActivity;
import com.hb.rimi.angel.bean.Order;
import com.hb.rimi.angel.util.StringUtil;

import java.util.List;

/**
 * Created by rimi on 2016/6/8.
 */
public class CheckPaymentAdapter extends RecyclerView.Adapter<CheckPaymentAdapter.MyViewHolder> {
    private Context mContext;
    private List<Order> orders;
    private LayoutInflater layoutInflater;


    public CheckPaymentAdapter(Context mContext, List<Order> orders) {
        this.mContext = mContext;
        this.orders = orders;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CheckPaymentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_check_payment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CheckPaymentAdapter.MyViewHolder holder, int position) {
        holder.ica_tv_check_name.setText(orders.get(position).getITEM_NAME());
        //TODO判断预约时间是否为空，为空则直接显示输入时间,否则显示预约时间
        if (StringUtil.isBlank(orders.get(position).getAPPOINTMENT())) {
            holder.ica_tv_date.setText(orders.get(position).getINPUT_DATE());
        } else {
            holder.ica_tv_date.setText(orders.get(position).getAPPOINTMENT());
        }
        holder.ica_tv_item_price.setText("数量："+orders.get(position).getAMOUNT());
        holder.ica_tv_amount.setText("单价：￥" +orders.get(position).getITEM_PRICE());
        holder.ica_tv_cost.setText("￥" + orders.get(position).getCOSTS());
        if ("0".equals(orders.get(position).getSTATUS())) {
            holder.ica_tv_status.setText("未付款");
        } else {
            holder.ica_tv_status.setText("已付款");
        }

    }


    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ica_tv_check_name, ica_tv_date, ica_tv_cost, ica_tv_status;

        TextView ica_tv_item_price, ica_tv_repetition, ica_tv_amount;

        public MyViewHolder(View itemView) {
            super(itemView);
            ica_tv_check_name = (TextView) itemView.findViewById(R.id.ica_tv_check_name);
            ica_tv_date = (TextView) itemView.findViewById(R.id.ica_tv_date);
            ica_tv_cost = (TextView) itemView.findViewById(R.id.ica_tv_cost);
            ica_tv_status = (TextView) itemView.findViewById(R.id.ica_tv_status);

            ica_tv_item_price = (TextView) itemView.findViewById(R.id.ica_tv_item_price);
            ica_tv_repetition = (TextView) itemView.findViewById(R.id.ica_tv_repetition);
            ica_tv_amount = (TextView) itemView.findViewById(R.id.ica_tv_amount);

        }
    }
}
