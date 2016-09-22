package com.hb.rimi.angel.sql;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import com.hb.rimi.angel.app.ProjectApplication;
import com.hb.rimi.angel.bean.CarInfo;
import com.hb.rimi.angel.util.StringUtil;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by rimi on 2016/7/13.
 */
public class CarDbUtils {


    public static List<CarInfo> selectAll() {
        try {
            return ProjectApplication.db.findAll(CarInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    //是否存在了此pid
    public static boolean hasPoductId(Activity activity, long productId) {
        String product_id = "";
        try {
            Cursor cursor = ProjectApplication.db.execQuery("select * from carInfo where product_id=" + productId);
            while (cursor.moveToNext()) {
                //光标移动成功
                product_id += cursor.getString(cursor.getColumnIndex("product_id"));
//                activity.startManagingCursor(cursor);  //查找后关闭游标
                //把数据取出
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        return StringUtil.isNotBlank(product_id) ? true : false;
    }

    public static void insert(CarInfo carInfo) {
        try {
            ProjectApplication.db.save(carInfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void del(String id) {
        try {
            ProjectApplication.db.deleteById(CarInfo.class, id);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void update(CarInfo carInfo) {
        try {
            ProjectApplication.db.saveOrUpdate(carInfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
