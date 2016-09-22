package com.hb.rimi.angel.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by rimi on 2016/5/27.
 */
public class StringUtil {
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断密码是否是6-16位
     *
     * @param psd
     * @return
     */
    public static boolean judgePsd(String psd) {
        int len = psd.length();
        if (len < 6 || len > 16) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 截取小数点后的2位数 ，四舍五入 ，处理科学计数法 ，无穷。
     */

    public static String endTwoChar(Object obj) {
        String objStr = null;
        if (null != obj && !"".equals(obj) && !"NaN".equals(obj)
                && !"-infinity".equals(obj) && !"infinity".equals(obj)
                && !"-Infinity".equals(obj) && !"Infinity".equals(obj)) {
            // System.out.println("四舍五入前" + obj.toString());
            try {
                Double va = Double.valueOf(obj.toString());
                double d = va.doubleValue();
                BigDecimal bg = new BigDecimal(d);
                // 允许四舍五入
                bg.setScale(2, BigDecimal.ROUND_HALF_UP);
                objStr = bg.toPlainString();// 将科学计数转为字符串

                DecimalFormat df = new DecimalFormat("0.00");
                String res = df.format(Double.valueOf(objStr));
                // System.out.println("四舍五入后" + res);
                return res;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            verCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";

        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // 0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            verName = packInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
