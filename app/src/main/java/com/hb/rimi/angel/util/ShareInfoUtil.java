package com.hb.rimi.angel.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hb.rimi.angel.bean.ReportPacs;
import com.hb.rimi.angel.bean.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 此类存放首选项信息
 * Created by rimi on 2016/6/2.
 */
public class ShareInfoUtil {

    /**
     * 读取是否有新版本
     *
     * @param context
     * @param
     * @return
     */
    public static Boolean readIsNewVer(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("appVerInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getBoolean("newVer",false);
    }

    /**
     * 保存是否有新版本
     *
     * @param context
     * @param
     * @return
     */
    public static void saveIsNewVer(Context context,boolean value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("appVerInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean("newVer", value);
        editor.commit();
    }

    /**
     * 读取新增的会员信息
     *
     * @param context
     * @param key
     * @return
     */
    public static String readParams(Context context, String key) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("addUserInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString(key,"");
    }

    /**
     * 保存新增的会员信息
     *
     * @param context
     * @param key
     * @return
     */
    public static void saveParams(Context context, String key, String value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("addUserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存登录电话
     *
     * @param context
     * @param phone
     * @return
     */
    public static void saveLoginPhone(Context context, String phone) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("loginPhoneInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("phone", phone);
        editor.commit();
    }

    /**
     * 读取保存的登录电话
     *
     * @param context
     * @return
     */
    public static String readLoginPhone(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("loginPhoneInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("phone", "");
    }

    public static void saveLoginPwd(Context context, String pwd) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPwdInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pwd", pwd);
        editor.commit();
    }

    public static String readLoginPwd(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPwdInfo", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pwd", "");
    }

    /**
     * 保存百度channelId
     */
    public static void saveChannelId(Context context, String channelId) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "baiduPushInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();

        editor.putString("channelId", channelId);

        editor.commit();
    }

    /**
     * 读取百度channelId
     */
    public static String readChannelId(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "baiduPushInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("channelId", "");
    }

    /**
     * 保存环信账号
     */
    public static void saveEasemobToken(Context context, String esemobToken) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "esemobInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();

        editor.putString("esemobToken", esemobToken);

        editor.commit();
    }

    /**
     * 读取环信账号
     */
    public static String readEasemobToken(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "esemobInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("esemobToken", "");
    }

    /**
     * 保存登录成功后的token
     */
    public static void saveToken(Context context, String token) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "tokenInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();

        editor.putString("token", token);

        editor.commit();
    }

    /**
     * 读取登录成功后的cid
     */
    public static String readToken(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "tokenInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("token", "");
    }

    /**
     * 保存登录成功后的loginId
     */
    public static void saveLoginId(Context context, String loginId) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "loginIdInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();

        editor.putString("loginId", loginId);

        editor.commit();
    }

    /**
     * 读取登录成功后的cid
     */
    public static String readLoginId(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "loginIdInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("loginId", "");
    }

    /**
     * 保存登录成功后的cid
     */
    public static void saveCid(Context context, String cid) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "cidInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();

        editor.putString("cid", cid);

        editor.commit();
    }

    /**
     * 读取登录成功后的cid
     */
    public static String readCid(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                "cidInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("cid", "");
    }

    /**
     * 保存客服电话
     *
     * @param context
     * @param phone
     * @return
     */
    public static void savePhone(Context context, String phone) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("phoneInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("phone", phone);
        editor.commit();
    }

    /**
     * 读取保存的客服电话
     *
     * @param context
     * @return
     */
    public static String readPhone(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("phoneInfo", Context.MODE_PRIVATE);
        return mySharedPreferences.getString("phone", "");
    }

    /**
     * 保存客户信息
     */
    public static void saveReaultBean(Context context, UserInfo.ResultBean result1) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("result", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String Int_Sex = result1.getSex();
        //客户姓名
        String name = result1.getName();
        //生日
        String birthday = result1.getBirthday();
        //预产期
        String edc = result1.getEDC();
        //余额
        String ic_fee = result1.getIc_fee();
        //卡号
        String ic_no = result1.getIC_NO();
        //卡类型
        String ic_type = result1.getIc_type();
        //末次月经
        String menses = result1.getMenses();
        //手机
        String mobile = result1.getMobile();
        //月经周期天数
        String mWeek = result1.getMWeek();
        //是否Vip
        String rebateType = result1.getRebateType();
        //客户专属电话
        String CustomerManagerMobile = result1.getCustomerManagerMobile();
        editor.putString("name", name);
        editor.putString("birthday", birthday);
        editor.putString("edc", edc);
        editor.putString("ic_fee", ic_fee + "");
        editor.putString("ic_no", ic_no);
        editor.putString("ic_type", ic_type);
        editor.putString("menses", menses);
        editor.putString("mobile", mobile);
        editor.putString("mWeek", mWeek);
        editor.putString("Int_Sex", Int_Sex);
        editor.putString("rebateType", rebateType);
        editor.putString("CustomerManagerMobile",CustomerManagerMobile);
        editor.commit();
    }

    /**
     * 读取客户信息
     *
     * @param context
     * @return
     */
    public static UserInfo.ResultBean readResultBean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("result", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String birthday = sharedPreferences.getString("birthday", "");
        String edc = sharedPreferences.getString("edc", "");
        String ic_fee = sharedPreferences.getString("ic_fee", "0");
        String ic_no = sharedPreferences.getString("ic_no", "");
        String ic_type = sharedPreferences.getString("ic_type", "");
        String menses = sharedPreferences.getString("menses", "");
        String mobile = sharedPreferences.getString("mobile", "");
        String mWeek = sharedPreferences.getString("mWeek", "");
        String Int_Sex = sharedPreferences.getString("Int_Sex", "");
        String rebateType = sharedPreferences.getString("rebateType", "");
        String CustomerManagerMobile = sharedPreferences.getString("CustomerManagerMobile","");
        UserInfo.ResultBean result1 = new UserInfo.ResultBean();
        result1.setName(name);
        result1.setBirthday(birthday);
        result1.setEDC(edc);
        result1.setIc_fee(ic_fee);
        result1.setIC_NO(ic_no);
        result1.setIc_type(ic_type);
        result1.setMenses(menses);
        result1.setMobile(mobile);
        result1.setMWeek(mWeek);
        result1.setSex(Int_Sex);
        result1.setRebateType(rebateType);
        result1.setCustomerManagerMobile(CustomerManagerMobile);
        return result1;
    }

    /**
     * 存储图片列表
     * @param context
     * @param list
     */
    /*public static void saveImgList(Context context,List<ReportPacs.ResultBean.ListBean> list){
        SharedPreferences sharedPreferences = context.getSharedPreferences("imgList",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("num",list.size());
        for(int i = 0;i<list.size();i++){
            String imageUrl = list.get(i).getImageUrl();
            editor.putString("imgurl"+i,imageUrl);
        }
        editor.commit();
    }

    public static List<ReportPacs.ResultBean.ListBean> readImgList(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("result", Context.MODE_PRIVATE);
        List<ReportPacs.ResultBean.ListBean> list = new ArrayList<>();
        int num = sharedPreferences.getInt("num", 0);
        for(int i = 0;i<num;i++){
            String string = sharedPreferences.getString("imgurl" + i, "");
            list.get(i).setImageUrl(string);
        }
        return list;
    }*/
    /**
     * 清除客户信息
     */
    public static void saveReaultBean(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("result", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //客户姓名
        String name = "";
        //性别
        String sex = "";
        //生日
        String birthday = "";
        //预产期
        String edc = "";
        //余额
        String ic_fee = "";
        //卡号
        String ic_no = "";
        //卡类型
        String ic_type = "";
        //末次月经
        String menses = "";
        //手机
        String mobile = "";
        //月经周期天数
        String mWeek = "";
        editor.putString("name", name);
        editor.putString("birthday", birthday);
        editor.putString("edc", edc);
        editor.putString("ic_fee", ic_fee + "");
        editor.putString("ic_no", ic_no);
        editor.putString("ic_type", ic_type);
        editor.putString("menses", menses);
        editor.putString("mobile", mobile);
        editor.putString("mWeek", mWeek);
        editor.putString("Int_Sex", sex);
        editor.commit();
    }
}
