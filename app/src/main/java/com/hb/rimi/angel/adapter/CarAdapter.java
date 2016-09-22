package com.hb.rimi.angel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.hb.rimi.angel.R;
import com.hb.rimi.angel.activity.marker.ShopCarActivity;
import com.hb.rimi.angel.bean.CarInfo;

import java.util.HashMap;
import java.util.List;

/**
 * 购物车适配器
 * Created by Administrator on 2016/5/29.
 */

public class CarAdapter extends BaseAdapter {
    public static HashMap<Integer, Boolean> isSelecteds;
    public static HashMap<Integer, Integer> tempNums;
    private List<CarInfo> packages;
    private LayoutInflater mInflater;
    private ViewHolder holder = null;
    private Context mContext;
    private ShopCarActivity act;
    private String fromTypeValue = "";

    public CarAdapter(Context context, List<CarInfo> packages, Activity act, String fromTypeValue) {
        this.mContext = context;
        this.packages = packages;
        this.act = (ShopCarActivity) act;
        this.fromTypeValue = fromTypeValue;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isSelecteds = new HashMap<Integer, Boolean>();
        tempNums = new HashMap<Integer, Integer>();
        initDate();
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelecteds;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        isSelecteds = isSelected;
    }

    // 初始化isSelected的数据
    public void initDate() {
        for (int i = 0; i < packages.size(); i++) {
            getIsSelected().put(i, false);
            tempNums.put(i, packages.get(i).getCount());
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return packages == null ? 0 : packages.size();
    }

    @Override
    public CarInfo getItem(int position) {
        return packages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return packages == null ? 0 : packages.size();
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        if (contentView == null) {
            holder = new ViewHolder();
            contentView = mInflater.inflate(R.layout.item_car, null, false);

            holder.item_package_chk = (CheckBox) contentView
                    .findViewById(R.id.item_car_chk);

            holder.item_package_tv_count = (TextView) contentView
                    .findViewById(R.id.item_car_tv_count);
            holder.item_package_tv_amount = (TextView) contentView
                    .findViewById(R.id.item_car_tv_price);
            holder.item_package_tv_name = (TextView) contentView
                    .findViewById(R.id.item_car_tv_name);
            holder.item_package_iv_increase = (ImageView) contentView
                    .findViewById(R.id.item_car_iv_increase);
            holder.item_package_iv_reduce = (ImageView) contentView
                    .findViewById(R.id.item_car_iv_reduce);
            holder.item_car_tv_gg = (TextView) contentView
                    .findViewById(R.id.item_car_tv_gg);

            //设置tag
            holder.item_package_tv_count.setTag(position);
            holder.item_package_iv_increase.setTag("jia" + position);
            holder.item_package_iv_reduce.setTag("jian" + position);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        final int tempItemNum = position;
        final View tempView = contentView;

        if (ShopCarActivity.fromTypeCarValue.equals(fromTypeValue)) {
            holder.item_package_tv_count.setText("" + tempNums.get(position));
            holder.item_package_chk.setVisibility(View.VISIBLE);
            holder.item_package_iv_reduce.setVisibility(View.VISIBLE);
            holder.item_package_iv_increase.setVisibility(View.VISIBLE);
            holder.item_package_chk.setChecked(getIsSelected().get(position));
            // 点击加
            holder.item_package_iv_increase.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int tag = Integer.valueOf(v.getTag().toString().replace("jia", ""));
                    TextView tv = (TextView) tempView.findViewWithTag(tag);
                    int i = 0;
                    if (tv != null) {
                        i = Integer.valueOf(tv.getText().toString());
                        tv.setText(String.valueOf(++i));
                    }
                    tempNums.put(tempItemNum, i);
                    act.updateInfo();
                }
            });
            // 点击减
            holder.item_package_iv_reduce.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int tag = Integer.valueOf(v.getTag().toString().replace("jian", ""));
                    TextView tv = (TextView) tempView.findViewWithTag(tag);
                    int i = 0;
                    if (tv != null) {
                        i = Integer.valueOf(tv.getText().toString());
                        if (--i > 0) {
                            tv.setText(String.valueOf(i));
                        } else {
                            tv.setText("1");
                        }
                    }
                    tempNums.put(tempItemNum, i);
                    act.updateInfo();
                }
            });

        } else {
            holder.item_package_tv_count.setText(packages.get(position).getCount() + "");
            holder.item_package_chk.setVisibility(View.GONE);
            holder.item_package_iv_reduce.setVisibility(View.INVISIBLE);
            holder.item_package_iv_increase.setVisibility(View.INVISIBLE);
        }
        //设置显示数量

        holder.item_package_tv_name.setText(packages.get(position).getGoodName());
        holder.item_package_tv_amount.setText("" + packages.get(position).getPrice());
        holder.item_car_tv_gg.setText(packages.get(position).getSpecifications());
//        if ("false".equals(fromTypeValue)) {
//            holder.item_package_chk.setVisibility(View.GONE);
//            holder.item_package_iv_reduce.setVisibility(View.GONE);
//            holder.item_package_iv_increase.setVisibility(View.GONE);
//        }
//        if ("true".equals(fromTypeValue)) {
//            holder.item_package_chk.setVisibility(View.GONE);
//            holder.item_package_iv_reduce.setVisibility(View.GONE);
//            holder.item_package_iv_increase.setVisibility(View.GONE);
//        }

        return contentView;
    }

    public class ViewHolder {

        public CheckBox item_package_chk;
        TextView item_package_tv_count, item_package_tv_amount, item_package_tv_name, item_car_tv_gg;
        ImageView item_package_iv_increase, item_package_iv_reduce;

    }
}