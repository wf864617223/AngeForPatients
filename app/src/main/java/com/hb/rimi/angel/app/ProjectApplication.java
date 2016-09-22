package com.hb.rimi.angel.app;

import android.app.Activity;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.hb.findim.app.HxApplication;
import com.hb.rimi.angel.util.CrashHandler;
import com.hb.rimi.angel.util.IntentManager;
import com.hb.rimi.angel.util.LogUtil;
import com.hyphenate.chatuidemo.DemoHelper;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 全局Application
 * Created by Administrator on 2016/5/14.
 */
public class ProjectApplication extends HxApplication {
    public static final String PACKAGE_NAME = "com.hb.rimi.angel";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    public static LogUtil logUtil;
    public static IntentManager intentManager;
    public static Context applicationContext;
    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    public static DbManager.DaoConfig daoConfig;
    private static ProjectApplication instance;
    private static ProjectApplication pinstance;
    // login user name
    public final String PREF_USERNAME = "username";
    private final String DB_NAME = "goodInfo";
    private final int DB_VERSION = 0;
    //回到首页
    private List<Activity> mList = new LinkedList<Activity>();
    public static DbManager db;

    public synchronized static ProjectApplication getInstanceApp() {
        if (null == pinstance) {
            pinstance = new ProjectApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        //初始化环信EsayUI --1

        MultiDex.install(this);
        super.onCreate();
        super.MyOnCreate(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        intentManager = IntentManager.getInstance(this);
        logUtil = LogUtil.getInstance(this, null, false);
        // 初始化 xutils框架
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志...
        //创建购物车数据库
        daoConfig = new DbManager.DaoConfig()
                .setDbName(DB_NAME)    //设置数据库名称
                .setDbDir(new File(DB_PATH + "/" + DB_NAME))  //数据库路径
                .setDbVersion(DB_VERSION)  //数据库版本
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
// TODO: ...
// db.addColumn(...);
// db.dropTable(...);
                    }
                });
        db = x.getDb(daoConfig);
        //初始化环信EsayUI --2
        applicationContext = this;
        instance = this;
        //init demo helper
        DemoHelper.getInstance().init(applicationContext);
        //环信红包注册
//        RedPacket.getInstance().initContext(applicationContext);
        //初始化百度推送
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY
                , "DMv8lq5Vu6AvVUnKf9MPTCnc");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
