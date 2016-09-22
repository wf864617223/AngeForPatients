package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ResBcheckInfo;

import java.util.List;


/**
 * 就诊提醒-B超检查信息
 * Created by rimi on 2016/6/1.
 */
public class BcheckInfoAdapter extends RecyclerView.Adapter<BcheckInfoAdapter.MyViewHolder> {
    List<ResBcheckInfo.ResultBean> resultBeanList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public BcheckInfoAdapter(Context mContext, List<ResBcheckInfo.ResultBean> resultBeanList) {
        this.mContext = mContext;
        this.resultBeanList = resultBeanList;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_bcheck_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.ibi_time.setText(resultBeanList.get(position).getAppTime());
        holder.ibi_sort.setText("" + resultBeanList.get(position).getSort());
        holder.ibi_appProject.setText(resultBeanList.get(position).getAppProject());
        holder.ibi_come_time.setText(resultBeanList.get(position).getComeTime());
    }

    @Override
    public int getItemCount() {
        return resultBeanList == null ? 0 : resultBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ibi_time, ibi_sort, ibi_appProject,ibi_come_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            ibi_time = (TextView) itemView.findViewById(R.id.ibi_time);
            ibi_sort = (TextView) itemView.findViewById(R.id.ibi_sort);
            ibi_appProject = (TextView) itemView.findViewById(R.id.ibi_appProject);
            ibi_come_time = (TextView) itemView.findViewById(R.id.ibi_come_time);
        }
    }
}
