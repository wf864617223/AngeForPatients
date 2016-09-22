package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.MessageList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/6/30.
 */
public class MyMessageAdapter extends BaseAdapter {
    private List<MessageList.ResultBean.ListBean> list;
    Context context;

    public MyMessageAdapter(List<MessageList.ResultBean.ListBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_message, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvMessageTitle.setText(list.get(position).getTitle());
        viewHolder.tvMessageContent.setText(list.get(position).getContent());
        long sendTime = list.get(position).getSendTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(sendTime);
        viewHolder.tvMessageSendtime.setText(sdf.format(date));
        long readTime = list.get(position).getReadTime();
        if(readTime == 0){
            viewHolder.tvMessageRead.setText("未读");
        }else{
            viewHolder.tvMessageRead.setText("已读");
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_message_title)
        TextView tvMessageTitle;
        @Bind(R.id.tv_message_content)
        TextView tvMessageContent;
        @Bind(R.id.tv_message_sendtime)
        TextView tvMessageSendtime;
        @Bind(R.id.tv_message_read)
        TextView tvMessageRead;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
