package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.marker.ShopCarActivity;
import com.hb.rimi.angel.activity.mine.MyOrderActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.PayOrderedBean;
import com.hb.rimi.angel.util.DisplayUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 2016/6/28.
 */
public class OrderedNewAdapter extends RecyclerView.Adapter<OrderedNewAdapter.MyViewHolder> {
    List<PayOrderedBean.ResultBean.ListBean> list;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Context mContext;
    private LayoutInflater layoutInflater;

    public OrderedNewAdapter(List<PayOrderedBean.ResultBean.ListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_frag_ordered, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.llImgGroup.setTag("" + position);
        List<PayOrderedBean.ResultBean.ListBean.DetailsBean> details = list.get(position).getDetails();
        if (("" + position).equals(holder.llImgGroup.getTag())) {
            if (holder.llImgGroup.getChildCount() == 0) {
                for (int i = 0; i < details.size(); i++) {
                    ImageView imageView = new ImageView(mContext);
                    String image = details.get(i).getImage();
                    String url = "http://app.cdangel.com" + image;
                    Glide.with(mContext).load(url).placeholder(R.mipmap.nopicture).error(R.mipmap.nopicture).into(imageView);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext, 80), DisplayUtil.dip2px(mContext, 80));
                    lp.setMargins(DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(lp);

                    holder.llImgGroup.addView(imageView);
                }
            } else {
                holder.llImgGroup.removeAllViews();
                for (int i = 0; i < details.size(); i++) {
                    ImageView imageView = new ImageView(mContext);
                    String image = details.get(i).getImage();
                    String url = "http://app.cdangel.com" + image;
                    Glide.with(mContext).load(url).placeholder(R.mipmap.nopicture).error(R.mipmap.nopicture).into(imageView);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mContext, 80), DisplayUtil.dip2px(mContext, 80));
                    lp.setMargins(DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5), DisplayUtil.dip2px(mContext, 5));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(lp);

                    holder.llImgGroup.addView(imageView);
                }
            }
        }


        holder.tvAllbusNum.setText("共计" + details.size() + "种商品");
        long createTime = list.get(position).getCreateTime();
        Date date = new Date(createTime);
        holder.tvOrdertime.setText("" + sdf.format(date));
        holder.tvBusMoney.setText("金额：￥" + list.get(position).getPrice());
        holder.llImgGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里跳到未支付订单页面
                System.out.println("未支付" + MyOrderActivity.isPayed);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ShopCarActivity.fromTypeKey, MyOrderActivity.isPayed);
                bundle.putInt("pso0", position);
                ProjectApplication.intentManager.toShopCarActivity(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrdertime;
        LinearLayout llImgGroup;
        HorizontalScrollView hsvImgGroup;
        TextView tvAllbusNum;
        TextView tvBusMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvOrdertime = (TextView) itemView.findViewById(R.id.tv_ordertime);
            llImgGroup = (LinearLayout) itemView.findViewById(R.id.ll_imgGroup);
            hsvImgGroup = (HorizontalScrollView) itemView.findViewById(R.id.hsv_imgGroup);
            tvAllbusNum = (TextView) itemView.findViewById(R.id.tv_allbus_num);
            tvBusMoney = (TextView) itemView.findViewById(R.id.tv_bus_money);
        }
    }
}
