package com.hb.rimi.angel.util;

import android.content.Context;

import com.hb.rimi.angel.bean.UserInfo;

/**
 * Created by hp on 2016/6/16.
 */
public class VocationalUtil {
    /**
     * 判断是否是存在cid,没有的话则显示
     */
    public static boolean hasCid(Context context) {
        try {
            String cid = ShareInfoUtil.readCid(context);

            if (StringUtil.isNotBlank(cid)&&!"0".equals(cid)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 判断是否是存在就诊卡号,没有的话则显示
     */
    public static boolean hasIcNo(Context context) {
        try {
            UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(context);
            String icNo = resultBean.getIC_NO();
            if (StringUtil.isBlank(icNo)) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否有末次月经
     */
    public static boolean hasMenses(Context context) {
        try {
            UserInfo.ResultBean resultBean = ShareInfoUtil.readResultBean(context);
            String isMenses = resultBean.getMenses();
            if (StringUtil.isBlank(isMenses)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
