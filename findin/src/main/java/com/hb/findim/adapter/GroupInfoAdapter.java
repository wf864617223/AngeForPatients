package com.hb.findim.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hb.findim.FindNewFragment;
import com.hb.findim.R;
import com.hb.findim.bean.ResHxGroup;
import com.hb.findim.view.WarningDialog;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;


/**
 * 公开
 * Created by rimi on 2016/6/1.
 */
public class GroupInfoAdapter extends RecyclerView.Adapter<GroupInfoAdapter.MyViewHolder> {
    WarningDialog dialog;
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (dialog != null) {
                        dialog.closeDialog();
                    }
                    if (findNewFragment != null) {
                        findNewFragment.update();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Context mContext;
    private List<ResHxGroup.ResultBean> groupInfos;
    private LayoutInflater layoutInflater;
    private FindNewFragment findNewFragment;

    public GroupInfoAdapter(Context mContext, List<ResHxGroup.ResultBean> groupInfos, FindNewFragment findNewFragment) {
        this.mContext = mContext;
        this.groupInfos = groupInfos;
        this.findNewFragment = findNewFragment;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fi_item_group, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.lib_find_tv_name.setText(groupInfos.get(position).getGroupname());
        holder.lib_find_tv_des.setText(groupInfos.get(position).getDescription());
        holder.lib_find_tv_mannum.setText("人数：" + groupInfos.get(position).getAffiliations());
        if (!"".equals(groupInfos.get(position).getImg())) {
            Glide.with(mContext).load(groupInfos.get(position).getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.lib_find_iv_aver);
        }

        holder.lib_find_rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new WarningDialog(mContext);
                dialog.setName(groupInfos.get(position).getGroupname());
                dialog.onOK(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String id = groupInfos.get(position).getGroupid();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    EMClient.getInstance().groupManager().joinGroup(id);//需异步处理
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                } finally {
                                    myHandler.sendEmptyMessage(0);
                                }

                            }
                        }).start();
                    }
                });
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

        ImageView lib_find_iv_aver;
        TextView lib_find_tv_mannum;

        public MyViewHolder(View itemView) {
            super(itemView);
            lib_find_tv_name = (TextView) itemView.findViewById(R.id.lib_find_tv_name);
            lib_find_tv_des = (TextView) itemView.findViewById(R.id.lib_find_tv_des);
            lib_find_rl_root = (RelativeLayout) itemView.findViewById(R.id.lib_find_ll_root);
            lib_find_ll_plus = (LinearLayout) itemView.findViewById(R.id.lib_find_ll_plus);
            lib_find_iv_aver = (ImageView) itemView.findViewById(R.id.lib_find_iv_aver);
            lib_find_tv_mannum = (TextView) itemView.findViewById(R.id.lib_find_tv_mannum);
        }
    }

}
