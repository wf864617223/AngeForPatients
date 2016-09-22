package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.UserYuyue;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/16.
 */
public class UserYuyueAdapter extends BaseAdapter {
    Context context;
    private List<UserYuyue.ResultBean> result1;

    public UserYuyueAdapter(Context context, List<UserYuyue.ResultBean> result1) {
        this.context = context;
        this.result1 = result1;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.list_user_yuyue, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.tvBESPOKETIME.setSelected(true);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(result1.get(position).getBESPOKE_TIME()!=null){
            viewHolder.tvBESPOKETIME.setText("预约时间:"+ result1.get(position).getBESPOKE_TIME().substring(0,10));
        }
        viewHolder.tvDOCTORNAME.setText("医生："+result1.get(position).getDOCTOR_NAME());
        viewHolder.tvICTYPE.setText(result1.get(position).getIC_TYPE());
        viewHolder.tvITEMNAME.setText(result1.get(position).getITEM_NAME());
        //viewHolder.tvOUTPNO.setText(result1.get(position).getOUTP_NO());
        viewHolder.tvPATIENTNAME.setText("就诊人："+result1.get(position).getPATIENT_NAME());
        viewHolder.tvPHONE.setText(result1.get(position).getPHONE());
        return convertView;
    }

    static class ViewHolder {
        /*@Bind(R.id.tv_OUTP_NO)
        TextView tvOUTPNO;*/
        @Bind(R.id.tv_PATIENT_NAME)
        TextView tvPATIENTNAME;
        @Bind(R.id.tv_PHONE)
        TextView tvPHONE;
        @Bind(R.id.tv_DOCTOR_NAME)
        TextView tvDOCTORNAME;
        @Bind(R.id.tv_ITEM_NAME)
        TextView tvITEMNAME;
        @Bind(R.id.tv_BESPOKE_TIME)
        TextView tvBESPOKETIME;
        @Bind(R.id.tv_IC_TYPE)
        TextView tvICTYPE;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
