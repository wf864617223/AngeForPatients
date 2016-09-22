package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.ReportPacs;

import org.xutils.x;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/14.
 */
public class TypePicsAdapter extends BaseAdapter {

    private List<ReportPacs.ResultBean.ListBean> list;
    Context context;

    public TypePicsAdapter(List<ReportPacs.ResultBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_report_img, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String imageUrl = list.get(position).getImageUrl();
        //x.image().bind(viewHolder.ivReportImage,imageUrl);
        Glide.with(context).load(imageUrl).placeholder(R.mipmap.nopicture).into(viewHolder.ivReportImage);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_report_image)
        ImageView ivReportImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
