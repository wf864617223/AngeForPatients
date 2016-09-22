package com.hb.rimi.angel.receiver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.hb.rimi.angel.activity.MainActivity;
import com.hb.rimi.angel.activity.home.MedicalVoucherActivity;
import com.hb.rimi.angel.activity.mine.LoginActivity;
import com.hb.rimi.angel.activity.mine.MyMessageActivity;
import com.hb.rimi.angel.util.ShareInfoUtil;
import com.hb.rimi.angel.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BdPushReceiver extends PushMessageReceiver {
    /**
     * TAG to Log
     */
    public static final String TAG = BdPushReceiver.class.getSimpleName();

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        // TODO Auto-generated method stub
        System.out.println("onBind----" + "errorCode==" + errorCode
                + " appid==" + appid + " userId==" + userId + " channelId=="
                + channelId + " requestId==" + requestId);
        //保存channelId
        ShareInfoUtil.saveChannelId(context, channelId);

    }

    @Override
    public void onDelTags(Context arg0, int arg1, List<String> arg2,
                          List<String> arg3, String arg4) {
        // TODO Auto-generated method stub
        System.out.println("onDelTags");
    }

    @Override
    public void onListTags(Context arg0, int arg1, List<String> arg2,
                           String arg3) {
        // TODO Auto-generated method stub
        System.out.println("onListTags");
    }

    /**
     * 接收透传消息的函数。
     *
     * @param context       上下文
     * @param message       推送的消息
     * @param contentString 自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message, String contentString) {
        // TODO Auto-generated method stub
        System.out.println("onMessage---- message==" + message
                + " contentString==" + contentString);

    }

    /**
     * 接收通知到达的函数。
     *
     * @param context       上下文
     * @param title         推送的通知的标题
     * @param description   推送的通知的描述
     * @param contentString 自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String contentString) {
        // TODO Auto-generated method stub
        System.out.println("onNotificationArrived----- title==" + title
                + " description==" + description + " contentString=="
                + contentString);
    }

    /**
     * 跳转到我的消息中心
     *
     * @param context
     * @return
     */
    private Intent[] makeIntentStack(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, MainActivity.class));
        intents[0].setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        intents[1] = new Intent(context, MyMessageActivity.class);
        return intents;
    }

    /**
     * 跳转到就诊凭证中心
     *
     * @param context
     * @return
     */
    private Intent[] makeMeidcaLIntentStack(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, MainActivity.class));
        intents[0].setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        intents[1] = new Intent(context, MedicalVoucherActivity.class);
        return intents;
    }

    /**
     * 判断消息类别做时间分发
     *
     * @param customContentString
     */
    public String getMessageType(String customContentString) {
        String type = null;
        if (StringUtil.isNotBlank(customContentString)) {
            try {
                JSONObject jsonObject = new JSONObject(customContentString);
                if (jsonObject.has("type")) {
                    type = jsonObject.getString("type");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return type;
    }

    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        // TODO Auto-generated method stub
        System.out.println("customContentString=>" + customContentString);
        String type = getMessageType(customContentString);
        //判断app进程是否存活
        if (isAppAlive(context, "com.hb.rimi.angel")) {
            //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
            //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
            //DetailActivity前，要先启动MainActivity。
            Log.i("NotificationReceiver", "the app process is alive");
            String isLogin = ShareInfoUtil.readParams(context, "isLogin");
            if ("true".equals(isLogin)) {
//                Intent msgIntent = new Intent(context, MyMessageActivity.class);
//                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
//                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
//                //如果Task栈不存在MainActivity实例，则在栈顶创建
//                msgIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        | Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(msgIntent);
                System.out.println("type=>" + type);
                if ("提示".equals(type)) {//跳转到就诊凭证
                    context.startActivities(makeMeidcaLIntentStack(context));
                    System.out.println("提示=>" + customContentString);
                } else if ("系统".equals(type)) {//跳转到消息列表
                    context.startActivities(makeIntentStack(context));
                    System.out.println("系统=>" + customContentString);
                }
            } else {
                Intent loginIntent = new Intent(context, LoginActivity.class);
                //将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
                //如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
                //如果Task栈不存在MainActivity实例，则在栈顶创建
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                if ("提示".equals(type)) {//跳转到就诊凭证
                    loginIntent.putExtra("onNotification", "truetrue");
                } else if ("系统".equals(type)) {//跳转到消息列表
                    loginIntent.putExtra("onNotification", "true");
                }
                context.startActivity(loginIntent);
            }

        } else {
            //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入             //参数跳转到DetailActivity中去了
            Log.i("NotificationReceiver", "the app process is dead");
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("com.hb.rimi.angel");
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            launchIntent.putExtra("onNotification", "true");
            if ("提示".equals(type)) {//跳转到就诊凭证
                launchIntent.putExtra("onNotification", "truetrue");
            } else if ("系统".equals(type)) {//跳转到消息列表
                launchIntent.putExtra("onNotification", "true");
            }
            context.startActivity(launchIntent);
        }
    }

    @Override
    public void onSetTags(Context arg0, int arg1, List<String> arg2,
                          List<String> arg3, String arg4) {
        // TODO Auto-generated method stub
        System.out.println("onSetTags");
    }

    @Override
    public void onUnbind(Context arg0, int arg1, String arg2) {
        // TODO Auto-generated method stub
        System.out.println("onUnbind");
    }
}
