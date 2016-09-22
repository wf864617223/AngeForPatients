package com.hb.rimi.angel.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.rimi.angel.R;

/**
 * 底部菜单
 * Created by rimi on 2016/5/26.
 */
public class ViewIndicator extends LinearLayout implements View.OnClickListener {
    //底部文字未选中与选中颜色
    private static final int COLOR_UNSELECT = Color.argb(100, 123, 128, 128);
    private static final int COLOR_SELECT = Color.argb(100, 17, 168, 187);
    private static final int LINE_COLOR = Color.argb(100, 221, 221, 221);
    //    private static final int COLOR_SELECT = Color.WHITE;
    // 对应的图标Tag
    private static final String TAG_ICON_0 = "icon_tag_0";
    private static final String TAG_ICON_1 = "icon_tag_1";
    private static final String TAG_ICON_2 = "icon_tag_2";
    private static final String TAG_ICON_3 = "icon_tag_3";
    // 对应的文字Tag
    private static final String TAG_TEXT_0 = "text_tag_0";
    private static final String TAG_TEXT_1 = "text_tag_1";
    private static final String TAG_TEXT_2 = "text_tag_2";
    private static final String TAG_TEXT_3 = "text_tag_3";
    private static int mCurIndicator;//当前选定View
    private static View[] mIndicators;//View集合
    private int mDefaultIndicator = 0;//默认选定View
    private OnIndicateListener mOnIndicateListener;

    //重写两个构造函数
    public ViewIndicator(Context context) {
        super(context);
    }

    public ViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurIndicator = mDefaultIndicator;//初始化话设置默认选择View
        setOrientation(LinearLayout.HORIZONTAL);//水平布局
        init();
    }

    public static void setIndicator(int which) {
        //清除之前的状态
        // mIndicators[mCurIndicator].setBackgroundResource(R.drawable.main_tab_item_bg_normal);
        ImageView prevIcon;
        TextView prevText;
        switch (mCurIndicator) {
            case 0:
                prevIcon = (ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_0);
                prevIcon.setImageResource(R.drawable.main_tab_item_home);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_0);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
            case 1:
                prevIcon = (ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_1);
                prevIcon.setImageResource(R.drawable.main_tab_item_find);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_1);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
            case 2:
                prevIcon = (ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_2);
                prevIcon.setImageResource(R.drawable.main_tab_item_shop);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_2);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
            case 3:
                prevIcon = (ImageView) mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_3);
                prevIcon.setImageResource(R.drawable.main_tab_item_mine);
                prevText = (TextView) mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_3);
                prevText.setTextColor(COLOR_UNSELECT);
                break;
        }
        //更新当前状态
        // mIndicators[which].setBackgroundResource(R.drawable.main_tab_item_bg_focus);

        ImageView currIcon;
        TextView currText;
        /**
         * 设置选中状态
         */
        switch (which) {
            case 0:
                currIcon = (ImageView) mIndicators[which].findViewWithTag(TAG_ICON_0);
                currIcon.setImageResource(R.mipmap.tab_home_focus);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_0);
                currText.setTextColor(COLOR_SELECT);
                break;
            case 1:
                currIcon = (ImageView) mIndicators[which].findViewWithTag(TAG_ICON_1);
                currIcon.setImageResource(R.mipmap.tab_find_focus);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_1);
                currText.setTextColor(COLOR_SELECT);
                break;
            case 2:
                currIcon = (ImageView) mIndicators[which].findViewWithTag(TAG_ICON_2);
                currIcon.setImageResource(R.mipmap.tab_shop_focus);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_2);
                currText.setTextColor(COLOR_SELECT);
                break;
            case 3:
                currIcon = (ImageView) mIndicators[which].findViewWithTag(TAG_ICON_3);
                currIcon.setImageResource(R.mipmap.tab_mine_focus);
                currText = (TextView) mIndicators[which].findViewWithTag(TAG_TEXT_3);
                currText.setTextColor(COLOR_SELECT);
                break;
        }
        mCurIndicator = which;
    }

    /**
     * @param iconResID     图片资源ID
     * @param stringResID   文字资源ID
     * @param stringColorID 颜色资源ID
     * @param iconTag       图片标签
     * @param textTag       文字标签
     * @return
     */
    public View createIndicator(int iconResID, int stringResID, int stringColorID, String iconTag, String textTag) {
        //实例化一个LineaLayout
        LinearLayout view = new LinearLayout(getContext());
        //垂直布局
        view.setOrientation(LinearLayout.VERTICAL);
        view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        view.setGravity(Gravity.CENTER_HORIZONTAL);
        view.setPadding(0, 0, 0, 12);
        view.setBackgroundResource(R.mipmap.main_tab_item_bg_normal);

        View lineView = new View(getContext());

        //设置ImageView宽高和权重
        lineView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 2, 1));
        lineView.setBackgroundColor(LINE_COLOR);

        ImageView iconView = new ImageView(getContext());
        //设置于该ImageView视图相关联的标记
        iconView.setTag(iconTag);
        //设置ImageView宽高和权重
        LinearLayout.LayoutParams lvparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
        lvparams.setMargins(0, 12, 0, 0);
        iconView.setLayoutParams(lvparams);

        //设置图片资源
        iconView.setImageResource(iconResID);
        TextView textView = new TextView(getContext());
        textView.setTag(textTag);
        LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
        llparams.setMargins(0, 12, 0, 0);
        textView.setLayoutParams(llparams);
        textView.setTextColor(stringColorID);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        //设置文字资源
        textView.setText(stringResID);

        view.addView(lineView);
        view.addView(iconView);
        view.addView(textView);

        return view;
    }

    private void init() {
        mIndicators = new View[4];//4个View

        //第一个为默认选中显示的
        //首页
        mIndicators[0] = createIndicator(R.mipmap.tab_home_focus, R.string.tab_item_home, COLOR_SELECT, TAG_ICON_0, TAG_TEXT_0);
        mIndicators[0].setBackgroundResource(R.drawable.main_tab_item_bg);
        mIndicators[0].setTag(Integer.valueOf(0));
        mIndicators[0].setOnClickListener(this);
        addView(mIndicators[0]);
        //发现
        mIndicators[1] = createIndicator(R.mipmap.tab_find_normal, R.string.tab_item_find, COLOR_UNSELECT, TAG_ICON_1, TAG_TEXT_1);
        mIndicators[1].setBackgroundResource(R.drawable.main_tab_item_bg);
        mIndicators[1].setTag(Integer.valueOf(1));
        mIndicators[1].setOnClickListener(this);
        addView(mIndicators[1]);
        //商城
        mIndicators[2] = createIndicator(R.mipmap.tab_shop_normal, R.string.tab_item_shop, COLOR_UNSELECT, TAG_ICON_2, TAG_TEXT_2);
        mIndicators[2].setBackgroundResource(R.drawable.main_tab_item_bg);
        mIndicators[2].setTag(Integer.valueOf(2));
        mIndicators[2].setOnClickListener(this);
        addView(mIndicators[2]);
        //我的
        mIndicators[3] = createIndicator(R.mipmap.tab_mine_normal, R.string.tab_item_my, COLOR_UNSELECT, TAG_ICON_3, TAG_TEXT_3);
        mIndicators[3].setBackgroundResource(R.drawable.main_tab_item_bg);
        mIndicators[3].setTag(Integer.valueOf(3));
        mIndicators[3].setOnClickListener(this);
        addView(mIndicators[3]);
    }

    public void setOnIndicateListener(OnIndicateListener listener) {
        mOnIndicateListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnIndicateListener != null) {
            int tag = (Integer) v.getTag();
            switch (tag) {
                case 0:
                    if (mCurIndicator != 0) {
                        mOnIndicateListener.onIndicate(v, 0);
                        setIndicator(0);
                    }
                    break;
                case 1:
                    if (mCurIndicator != 1) {
                        mOnIndicateListener.onIndicate(v, 1);
                        setIndicator(1);
                    }
                    break;
                case 2:
                    if (mCurIndicator != 2) {
                        mOnIndicateListener.onIndicate(v, 2);
                        setIndicator(2);
                    }
                    break;
                case 3:
                    if (mCurIndicator != 3) {
                        mOnIndicateListener.onIndicate(v, 3);
                        setIndicator(3);
                    }
                    break;
            }
        }
    }

    public interface OnIndicateListener {
        public void onIndicate(View v, int which);
    }
}
