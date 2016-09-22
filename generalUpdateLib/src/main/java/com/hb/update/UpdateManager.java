package com.hb.update;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hb.util.SdUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateManager {
    private static String TAG = UpdateManager.class.getSimpleName();
    private static double LIMIT_SIZE = 50;//限制安装最小容量(MB)(需在0~1024)
    private static String apkUrl = "";//apk下载地址
    private static final String saveDir = "/GeneralUpdateLib/";//保存文件夹名称
    private static final String saveDirName = "update.apk";//保存文件夹下文件名称
    private static final int DOWN_UPDATE = 1;//下载中
    private static final int DOWN_OVER = 2;//下载完成

    private Context mContext;
    private String availableSdPath = null;//可用的sd卡路径
    private ProgressBar mProgress;// 进度条
    private TextView messageView;// 显示百分比文本
    private Thread downLoadThread;
    private int progress;
    private boolean interceptFlag = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
//                    mProgress.setSecondaryProgress(progress + 23);//缓冲进度
                    mProgress.setProgress(progress);
                    messageView.setText("正在下载更新程序：" + progress + "%");
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Context context) {
        this.mContext = context;
        this.apkUrl = Config.UpDate_URL;
    }

    /**
     * 外部接口让主Activity调用
     */

    public void checkUpdateInfo() {
        showNoticeDialog();
    }


    private Runnable mDownApkRunnable = new Runnable() {
        @Override
        public void run() {
            FileOutputStream outputStream = null;
            HttpURLConnection conn = null;
            InputStream is = null;
            boolean isSuc = false;
            File apkFile = null;
            String apkFilePath = null;
            try {
                URL url = new URL(apkUrl);
                conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");//防止返回-1
//                conn.setConnectTimeout(6000);
//                conn.setReadTimeout(6000);
                conn.connect();
                int length = conn.getContentLength();
                is = conn.getInputStream();
                //获取可用的SD卡路径
                availableSdPath = SdUtils.isAllSdEnough(LIMIT_SIZE);
                Log.d(TAG, "availableSdPath路径==" + availableSdPath);
                //判断sd卡容量是否大于等于50M
                if (null != availableSdPath) {
                    File file = new File(availableSdPath + saveDir);
                    if (!file.exists()) {
                        isSuc = file.mkdir();
                        if (isSuc) {
                            apkFilePath = availableSdPath + saveDir + saveDirName;
                            apkFile = new File(apkFilePath);
                        } else {
                            Log.d(TAG, "文件夹" + availableSdPath + saveDir + "创建失败");
                        }
                    } else {
                        apkFilePath = availableSdPath + saveDir + saveDirName;
                        apkFile = new File(apkFilePath);
                    }
                    //授予此路径具有777 rwx权限
                    chmodPath(apkFilePath);

                    outputStream = new FileOutputStream(apkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        if (numread != -1) {
                            count += numread;
                        }
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            // 下载完成通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                            break;
                        }
                        outputStream.write(buf, 0, numread);
                    } while (!interceptFlag);// 点击取消就停止下载.
                    outputStream.flush();
                    outputStream.close();
                    is.close();
                } else {
                    Log.d(TAG, "内存卡无效或容量无效，尝试将apk存放在应用内,注意添加读写权限。");
                    outputStream = mContext.openFileOutput(saveDirName,
                            Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    do {
                        int numread = is.read(buf);
                        if (numread != -1) {
                            count += numread;
                        }
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            // 下载完成通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                            break;
                        }
                        outputStream.write(buf, 0, numread);
                    } while (!interceptFlag);// 点击取消就停止下载.
                    outputStream.flush();
                    outputStream.close();
                    is.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.close();
                    is.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 授予777 rwx权限
     *
     * @param filePath
     */
    public void chmodPath(String filePath) {
        try {
            String[] args2 = {"chmod", "777", filePath};
            Runtime.getRuntime().exec(args2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装apk
     */

    private void installApk() {
        File apkFilePath = new File(availableSdPath + saveDir + saveDirName);
        if (!apkFilePath.exists()) {
            //开始尝试使用系统的安装路径
            String filePath = mContext.getFilesDir().getPath();
            //赋予读写权限777否则apk解析失败
            chmodPath(filePath);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + filePath + File.separator + saveDirName),
                    "application/vnd.android.package-archive");
            mContext.startActivity(i);
            Log.d(TAG, "file://" + filePath + File.separator + saveDirName);
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.setDataAndType(Uri.parse("file://" + apkFilePath.toString()),
                    "application/vnd.android.package-archive");

            Log.d(TAG, "file://" + apkFilePath.toString());
            mContext.startActivity(i);
        }
    }

    /**
     * 是否存在新版本
     * 如果要比较两个,则调用两次方法即可。
     *
     * @param context
     * @param isCompareVersionName true 比较versionName, false比较versionCode
     * @return
     */
    public static boolean getUpdateInfo(Context context, String jsonUrl,
                                        boolean isCompareVersionName) {
        if (isCompareVersionName) {// verName
            return getVerNameUpdateInfo(context, jsonUrl);
        } else {// verCode
            return getVerCodeUpdateInfo(context, jsonUrl);
        }
    }

    /**
     * 是否存在新版本 判断vercode
     *
     * @param context
     * @return
     */
    public static boolean getVerCodeUpdateInfo(Context context, String jsonUrl) {
        Config con = new Config(jsonUrl);
        int newVerCode = con.getServerVerCode();
        int oldVerCode = con.getVerCode(context);
        Log.d(TAG, "oldVerCode:" + oldVerCode
                + "<==>newVerCode:" + newVerCode);
        return (oldVerCode < newVerCode) ? true : false;
    }

    /**
     * 是否存在新版本 判断verName
     *
     * @param context
     * @return
     */
    public static boolean getVerNameUpdateInfo(Context context, String jsonUrl) {
        Config con = new Config(jsonUrl);
        int newVerName = con.getServerVerName();
        int oldVerName = Integer.parseInt(Config.getNum(con
                .getVerName(context)));
        Log.d(TAG, "oldVerName:" + oldVerName
                + "<==>newVerName:" + newVerName);
        return (oldVerName < newVerName) ? true : false;
    }

    /**
     * 显示新版本提示对话框
     */
    private void showNoticeDialog() {
        if (mContext == null) {
            Log.d(TAG, "上下文已经不存在");
            return;
        }
        final AlertDialog ad = new AlertDialog(mContext);
        ad.setAlertTitle(Config.getAppName(mContext) + "版本升级");
        ad.setContent(Config.newVerName);
        //设置跳转到浏览器下载文本值
        ad.setUrlHit(apkUrl);
        ad.setViewProgress(false);
        ad.setPositiveButton("立即升级", new OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                showDownloadDialog();
            }
        });
        ad.setNegativeButton("下次再说", new OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });

    }

    /**
     * 准备更新
     *
     * @param context
     */
    public static void newUpdate(Context context) {
        UpdateManager mUpdateManager = new UpdateManager(context);
        mUpdateManager.checkUpdateInfo();
    }

    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mDownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadDialog() {
        final AlertDialog ad = new AlertDialog(mContext);
        ad.setAlertTitle(Config.getAppName(mContext) + "版本升级");
        ad.setViewProgress(true);
        mProgress = ad.getProcessbar();
        messageView = ad.getTextView();
        ad.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
                interceptFlag = true;
            }
        });
        downloadApk();
    }
}
