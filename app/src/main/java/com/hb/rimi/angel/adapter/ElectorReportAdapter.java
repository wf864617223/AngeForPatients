package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ListReport;
import com.hb.rimi.angel.util.T;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/12.
 */
public class ElectorReportAdapter extends BaseAdapter {
    private List<ListReport.ResultBean> resultBeanList;
    Context context;

    public ElectorReportAdapter(List<ListReport.ResultBean> resultBeanList, Context context) {
        this.resultBeanList = resultBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resultBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return resultBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_check_report, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.tvCheckTime.setSelected(true);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String report_name = resultBeanList.get(i).getReport_Name();
        if(TextUtils.isEmpty(report_name)){
            viewHolder.tvReportName.setText("无名称");
        }else{
            viewHolder.tvReportName.setText(resultBeanList.get(i).getReport_Name());
        }
        String check_time = resultBeanList.get(i).getCheck_time();
        String[] split = check_time.split(" ");
        System.out.println("=="+split[0]+"----------"+split[1]);
        if(split[1].equals("0:00:00")){
            viewHolder.tvCheckTime.setText("检查时间:"+split[0]);
        }else{
            viewHolder.tvCheckTime.setText("检查时间:"+resultBeanList.get(i).getCheck_time());
        }
        String report_time = resultBeanList.get(i).getReport_time();
        String[] split1 = report_time.split(" ");
        if(split1.equals("0:00:00")){
            viewHolder.tvReportTime.setText("报告时间:"+split1[0]);
        }else{
            viewHolder.tvReportTime.setText("报告时间:"+resultBeanList.get(i).getReport_time());
        }

        // viewHolder.tvReportTime.setText("报告时间:"+resultBeanList.get(i).getReport_time());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_report_name)
        TextView tvReportName;
        @Bind(R.id.tv_check_time)
        TextView tvCheckTime;
        @Bind(R.id.tv_report_time)
        TextView tvReportTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
