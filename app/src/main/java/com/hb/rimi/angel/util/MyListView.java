package com.hb.rimi.angel.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by hp on 2016/6/13.
 * 可以使用这个类实现在ListView里面嵌套ListView
 */
public class MyListView extends GridView{
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
