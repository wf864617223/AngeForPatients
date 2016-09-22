package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ListBean;
import com.hb.rimi.angel.bean.ResponseLis;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/13.
 */
public class TypeLisAdapter extends BaseAdapter {
    private List<ResponseLis.ResultBean.ListBean> list;
    Context context;

    public TypeLisAdapter(List<ResponseLis.ResultBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.viewholder_result_lis, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCount.setText(""+i);
        viewHolder.tvItemName.setText(list.get(i).getItemName());
        viewHolder.tvRESULT.setText(list.get(i).getRESULT());
        viewHolder.tvREFERENCERANGE.setText(list.get(i).getREFERENCERANGE());
        String unit = list.get(i).getUNIT();
        String replace = unit.replace(" ", "");
        viewHolder.tvUNITOne.setText(replace);
        //viewHolder.tvUNITTwo.setText(list.get(i).getUNIT());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.tv_count)
        TextView tvCount;
        @Bind(R.id.tv_ItemName)
        TextView tvItemName;
        @Bind(R.id.tv_RESULT)
        TextView tvRESULT;
        @Bind(R.id.tv_UNIT_one)
        TextView tvUNITOne;
        @Bind(R.id.tv_REFERENCERANGE)
        TextView tvREFERENCERANGE;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
