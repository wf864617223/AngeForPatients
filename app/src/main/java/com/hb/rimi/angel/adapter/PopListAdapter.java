package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.BusStyleBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/7/4.
 */
public class PopListAdapter extends BaseAdapter {
    private List<BusStyleBean.ResultBean> result1;
    Context context;

    public PopListAdapter(List<BusStyleBean.ResultBean> result1, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPopList.setText(result1.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_pop_list)
        TextView tvPopList;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
