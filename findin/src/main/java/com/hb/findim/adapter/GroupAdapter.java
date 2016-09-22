package com.hb.findim.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hb.findim.R;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import com.hyphenate.easeui.EaseConstant;

import java.util.List;

/**
 * 私有
 * Created by rimi on 2016/6/1.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {
    private Context mContext;
    private List<EMGroup> groupInfos;
    private LayoutInflater layoutInflater;

    public GroupAdapter(Context mContext, List<EMGroup> groupInfos) {
        this.mContext = mContext;
        this.groupInfos = groupInfos;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fi_item_group, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.lib_find_tv_name.setText(groupInfos.get(position).getGroupName());
        holder.lib_find_tv_des.setText(groupInfos.get(position).getDescription());
        holder.lib_find_tv_mannum.setText("人数：" + groupInfos.get(position).getAffiliationsCount());
        holder.lib_find_ll_plus.setVisibility(View.GONE);
        holder.lib_find_rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE,
                        EaseConstant.CHATTYPE_GROUP);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,
                        groupInfos.get(position).getGroupId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupInfos == null ? 0 : groupInfos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView lib_find_tv_name;
        TextView lib_find_tv_des;
        RelativeLayout lib_find_rl_root;
        LinearLayout lib_find_ll_plus;
        TextView lib_find_tv_mannum;

        public MyViewHolder(View itemView) {
            super(itemView);
            lib_find_tv_name = (TextView) itemView.findViewById(R.id.lib_find_tv_name);
            lib_find_tv_des = (TextView) itemView.findViewById(R.id.lib_find_tv_des);
            lib_find_rl_root = (RelativeLayout) itemView.findViewById(R.id.lib_find_ll_root);
            lib_find_ll_plus = (LinearLayout) itemView.findViewById(R.id.lib_find_ll_plus);
            lib_find_tv_mannum = (TextView) itemView.findViewById(R.id.lib_find_tv_mannum);
            lib_find_ll_plus.setVisibility(View.GONE);
        }
    }
}
