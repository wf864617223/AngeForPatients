package com.hb.rimi.angel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.MainActivity;
import com.hb.rimi.angel.activity.marker.BusInfoActivity;
import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.BusListBean;
import com.hb.rimi.angel.contanst.HttpContanst;
import com.hb.rimi.angel.fragment.ShopFragment;

import java.util.List;

/**
 * Created by hp on 2016/7/4.
 */
public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.MyViewHolder> {
    List<BusListBean.ResultBean.ListBean> listBeanList;
    MainActivity activity;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public BusListAdapter(Context mContext, List<BusListBean.ResultBean.ListBean> listBeanList, Activity activity) {
        this.listBeanList = listBeanList;
        this.mContext = mContext;
        if (activity != null) {
            this.activity = (MainActivity) activity;
        }
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_grid_buslist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String image = listBeanList.get(position).getImage();
        if (image == null) {
            holder.ivBusimg.setImageResource(R.mipmap.woman);
        } else {
            String url = HttpContanst.SERVER_ADD + image;
            Glide.with(mContext).load(url).error(R.mipmap.nopicture).placeholder(R.mipmap.nopicture).into(holder.ivBusimg);
        }
        holder.tvBusname.setText(listBeanList.get(position).getName());
        holder.tvBusPrice.setText("￥" + listBeanList.get(position).getTradePrice());
        holder.tvSales.setText("销量：" + listBeanList.get(position).getSales());

        holder.igb_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity != null) {
                    if (activity.shopFragment.popupWindow != null && activity.shopFragment.popupWindow.isShowing()) {
                        activity.shopFragment.popupWindow.dismiss();
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("productId", "" + listBeanList.get(position).getId());
                ProjectApplication.intentManager.toBusInfoActivity(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList == null ? 0 : listBeanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBusimg;
        TextView tvBusname;
        TextView tvBusPrice;
        TextView tvSales;
        RelativeLayout igb_root;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivBusimg = (ImageView) itemView.findViewById(R.id.iv_busimg);
            tvBusname = (TextView) itemView.findViewById(R.id.tv_busname);
            tvBusPrice = (TextView) itemView.findViewById(R.id.tv_bus_price);
            tvSales = (TextView) itemView.findViewById(R.id.tv_sales);
            igb_root = (RelativeLayout) itemView.findViewById(R.id.igb_root);
        }
    }
}
//    private List<BusListBean.ResultBean.ListBean> result2List;
//    Context context;
//
//    public BusListAdapter(List<BusListBean.ResultBean.ListBean> result2List, Context context) {
//        this.result2List = result2List;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//        return result2List.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return result2List.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewHolder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_buslist, null);
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        String image = result2List.get(position).getImage();
//        if (image == null) {
//            viewHolder.ivBusimg.setImageResource(R.mipmap.woman);
//        } else {
//            /*byte[] byteIcon = Base64.decode(image,Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(byteIcon,0,byteIcon.length);
//            viewHolder.ivBusimg.setImageBitmap(bitmap);*/
//            String url = HttpContanst.SERVER_ADD + image;
//            Glide.with(context).load(url).error(R.mipmap.nopicture).placeholder(R.mipmap.nopicture).into(viewHolder.ivBusimg);
//        }
//
//        viewHolder.tvBusname.setText(result2List.get(position).getName());
//        viewHolder.tvBusPrice.setText("￥" + result2List.get(position).getTradePrice());
//        viewHolder.tvSales.setText("销量："+result2List.get(position).getSales());
//        return convertView;
//    }
//
//class ViewHolder {
//    @Bind(R.id.iv_busimg)
//    ImageView ivBusimg;
//    @Bind(R.id.tv_busname)
//    TextView tvBusname;
//    @Bind(R.id.tv_bus_price)
//    TextView tvBusPrice;
//    @Bind(R.id.tv_sales)
//    TextView tvSales;
//
//    ViewHolder(View view) {
//        ButterKnife.bind(this, view);
//    }
//}