package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/7.
 */
public class GroupNoaddAdapter extends BaseAdapter {
    private List<EMGroup> groupList;
    Context context;

    public GroupNoaddAdapter(List<EMGroup> groupList, Context context) {
        this.groupList = groupList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int i) {
        return groupList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.group_list_noadd, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvGroupTitle.setText(groupList.get(i).getGroupName());
        viewHolder.tvGroupIntroduct.setText(groupList.get(i).getDescription());
        viewHolder.tvGroupPeople.setText(groupList.get(i).getAffiliationsCount()+"人");

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_group_title)
        TextView tvGroupTitle;
        @Bind(R.id.tv_group_introduct)
        TextView tvGroupIntroduct;
        @Bind(R.id.tv_group_people)
        TextView tvGroupPeople;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
