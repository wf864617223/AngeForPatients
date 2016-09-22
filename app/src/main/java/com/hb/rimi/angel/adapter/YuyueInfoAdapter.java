package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.YuyueInfoBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/16.
 */
public class YuyueInfoAdapter extends BaseAdapter {

    private List<YuyueInfoBean.ResultBean> result1;
    Context context;

    public YuyueInfoAdapter(List<YuyueInfoBean.ResultBean> result1, Context context) {
        this.result1 = result1;
        this.context = context;
    }

    @Override
    public int getCount() {
        return result1.size();
    }

    @Override
    public Object getItem(int position) {
        return result1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_yuyue_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDEPTCODE.setText(result1.get(position).getDEPT_CODE());
        viewHolder.tvDEPTNAME.setText(result1.get(position).getDEPT_NAME());
        viewHolder.tvDOCCODE.setText(result1.get(position).getDOC_CODE());
        viewHolder.tvDOCNAME.setText(result1.get(position).getDOC_NAME());
        viewHolder.tvENSUREFLAG.setText(result1.get(position).getENSURE_FLAG());
        viewHolder.tvICCODE.setText(result1.get(position).getIC_CODE());
        viewHolder.tvINPUTDATE.setText("就诊时间："+result1.get(position).getINPUT_DATE());
        //viewHolder.tvOPERDATE.setText(result1.get(position).getOPER_DATE());
        viewHolder.tvPATIENTNAME.setText("就诊人："+result1.get(position).getPATIENT_NAME());
        viewHolder.tvREGISTERNO.setText(result1.get(position).getREGISTER_NO());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_PATIENT_NAME_info)
        TextView tvPATIENTNAME;
        @Bind(R.id.tv_INPUT_DATE)
        TextView tvINPUTDATE;
        @Bind(R.id.tv_REGISTER_NO)
        TextView tvREGISTERNO;
        @Bind(R.id.tv_IC_CODE)
        TextView tvICCODE;
        @Bind(R.id.tv_DOC_CODE)
        TextView tvDOCCODE;
        @Bind(R.id.tv_DOC_NAME)
        TextView tvDOCNAME;
        @Bind(R.id.tv_DEPT_CODE)
        TextView tvDEPTCODE;
        @Bind(R.id.tv_DEPT_NAME)
        TextView tvDEPTNAME;
        @Bind(R.id.tv_ENSURE_FLAG)
        TextView tvENSUREFLAG;
        //@Bind(R.id.tv_OPER_DATE)
        //TextView tvOPERDATE;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
