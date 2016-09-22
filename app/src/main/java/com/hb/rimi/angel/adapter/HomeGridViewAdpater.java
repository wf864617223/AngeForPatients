package com.hb.rimi.angel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hb.rimi.angel.HttpUtils.HttpLoadImg;
import com.hb.rimi.angel.R;
import com.hb.rimi.angel.bean.HomeMenu;

import java.util.List;

/**
 * Created by rimi on 2016/6/1.
 */
public class HomeGridViewAdpater extends RecyclerView.Adapter<HomeGridViewAdpater.MyViewHolder> {
    private Context mContext;
    private List<HomeMenu> menus;
    private LayoutInflater layoutInflater;

    public HomeGridViewAdpater(Context mContext, List<HomeMenu> menus) {
        this.mContext = mContext;
        this.menus = menus;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_home_menu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.item_home_menu_tv_title.setText(menus.get(position).getTitle());
        String imgUrl=menus.get(position).getImgUrl();
        boolean result=imgUrl.matches("[0-9]+");
        if (result == true) {
            HttpLoadImg.loadImg(mContext, Integer.valueOf(menus.get(position).getImgUrl()), holder.item_home_menu_iv_imgurl);
        }else{
            HttpLoadImg.loadImg(mContext, menus.get(position).getImgUrl(), holder.item_home_menu_iv_imgurl);

        }
    }

    @Override
    public int getItemCount() {
        return menus == null ? 0 : menus.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_home_menu_tv_title;
        ImageView item_home_menu_iv_imgurl;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_home_menu_tv_title = (TextView) itemView.findViewById(R.id.item_home_menu_tv_title);
            item_home_menu_iv_imgurl = (ImageView) itemView.findViewById(R.id.item_home_menu_iv_imgurl);
        }
    }
}
