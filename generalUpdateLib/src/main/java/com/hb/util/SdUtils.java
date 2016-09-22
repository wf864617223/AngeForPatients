package com.hb.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TXG on 2016/5/21.
 */
public class SdUtils {

    private static final String TAG = SdUtils.class.getSimpleName();

    /**
     * 判断所有sd卡路径是否可用且容量充足
     *
     * @param limitSize
     * @return 返回可用的SD卡路径
     */
    public static String isAllSdEnough(double limitSize) {
        String sdPath = null;//剩余空间是否足够
        Map<String, Object> sdFreeMap = null;//容量值及单位
        String innerSdPath = null;
        String outSdPath = null;
        String outSdPathOther = null;

        innerSdPath = SdUtils.getInnerSDpath();
        outSdPath = SdUtils.getOutSDpath();
        outSdPathOther = SdUtils.getExtPathOther();

        if (null != innerSdPath && !new File(innerSdPath).exists()) {
            innerSdPath = null;
        }
        if (null != outSdPath && !new File(outSdPath).exists()) {

            outSdPath = null;
        }
        if (null != outSdPathOther && !new File(outSdPathOther).exists()) {
            outSdPathOther = null;
        }

//        innerSdPath = null;
//        outSdPath = null;
//        outSdPathOther = null;

        Log.d(TAG, "sd卡路径1==" + innerSdPath);
        Log.d(TAG, "sd卡路径2==" + outSdPath);
        Log.d(TAG, "sd卡路径3==" + outSdPathOther);

        //判断是否可用
        if (isSingleSdEnough(limitSize, innerSdPath)) {
            sdPath = innerSdPath;
            return sdPath;
        } else {
            if (isSingleSdEnough(limitSize, outSdPath)) {
                sdPath = outSdPath;
                return sdPath;
            } else if (isSingleSdEnough(limitSize, outSdPathOther)) {
                sdPath = outSdPathOther;
                return sdPath;
            } else {
                Log.d(TAG, "暂未发现可用或容量足够的内存设备，请检查！");
            }
        }
        if (isSingleSdEnough(limitSize, outSdPath)) {
            sdPath = outSdPath;
            return sdPath;
        } else {
            if (isSingleSdEnough(limitSize, outSdPathOther)) {
                sdPath = outSdPathOther;
                return sdPath;
            } else {
                Log.d(TAG, "暂未发现可用或容量足够的内存设备，请检查！");
            }
        }
        if (isSingleSdEnough(limitSize, outSdPathOther)) {
            sdPath = outSdPathOther;
            return sdPath;
        } else {
            Log.d(TAG, "暂未发现可用或容量足够的内存设备，请检查！");
        }
        return sdPath;
    }

    /**
     * 单个sd卡路径是否可用且容量充足
     *
     * @param sdPath    SD卡路径
     * @param limitSize 安装需要容量
     * @return 返回当前sd卡是否可用
     */
    public static boolean isSingleSdEnough(double limitSize, String sdPath) {
        boolean isOk = false;//剩余空间是否足够
        Map<String, Object> sdFreeMap = null;//容量值及单位
        if (!StringUtils.isBlank(sdPath)) {
            sdFreeMap = SdUtils.getSDFreeSize(sdPath);
            if (sdFreeMap != null) {
                double sdSize = Double.valueOf(sdFreeMap.get("size").toString());
                String unit = sdFreeMap.get("unit").toString();
                Log.d(TAG, sdPath+" SD卡剩余容量(MB)==" + sdSize + unit);
                if (((sdSize >= limitSize) && ("MB".equals(unit))) || ("GB".equals(unit) || "TB".equals(unit))) {
                    isOk = true;
                    Log.d(TAG, sdPath+" SD卡容量充足可以使用。");
                } else {
                    Log.d(TAG, sdPath+" SD卡容量不足，已小于50MB。结束");
                    isOk = false;
                }
            } else {
                isOk = false;
                Log.d(TAG, sdPath+" 获取SD卡剩余容量失败，请检查内存卡是否正确！");
            }
        } else {
            isOk = false;
            Log.d(TAG, "暂未发现内置或外置SD卡，请检查！");
        }
        return isOk;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @param sdPath sd卡的路径 单位MB
     * @return 返回大小和单位
     */
    public static Map<String, Object> getSDFreeSize(String sdPath) {
        Map<String, Object> map = new HashMap<String, Object>();
        StatFs sf = new StatFs(sdPath);
        double blockSize;
        double freeBlocks;
        //空闲的数据块的数量
        if (Build.VERSION.SDK_INT > 17) {
            blockSize = sf.getBlockSizeLong();
            freeBlocks = sf.getAvailableBlocksLong();
        } else {
            blockSize = sf.getBlockSize();
            freeBlocks = sf.getAvailableBlocks();
        }
        //返回SD卡空闲大小
        double value = freeBlocks * blockSize;
        int conValue = 1024;
        DecimalFormat df = new DecimalFormat("#.00");

        if (value < conValue) {
            map.put("size", value);
            map.put("unit", "Byte");
        } else if (value / conValue < conValue) {
            map.put("size", df.format(value / conValue));
            map.put("unit", "KB");
        } else if (value / conValue / conValue < conValue) {
            map.put("size", df.format(value / conValue / conValue));
            map.put("unit", "MB");
        } else if (value / conValue / conValue / conValue < conValue) {
            map.put("size", df.format(value / conValue / conValue / conValue));
            map.put("unit", "GB");
        } else if (value / conValue / conValue / conValue / conValue < conValue) {
            map.put("size", df.format(value / conValue / conValue / conValue / conValue));
            map.put("unit", "TB");
        } else {
            return null;
        }
        return map;
    }

    /**
     * 获取已挂载内置SD卡路径
     *
     * @return 返回路径
     */
    public static String getInnerSDpath() {
        String sdState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(sdState) ? new File(Environment.getExternalStorageDirectory().getAbsolutePath()).getAbsolutePath() : null;//获取根路径
    }

    /**
     * 获取扩展SD卡存储目录(storage开头的)
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static String getOutSDpath() {
        try {
            String path = null;
            File sdCardFile = null;
            ArrayList<String> devMountList = getDevMountList();
            for (String devMount : devMountList) {
                File file = new File(devMount);
                if (file.isDirectory() && file.canWrite()) {
                    path = file.getAbsolutePath();
                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                    File testWritable = new File(path, "test_" + timeStamp);
                    if (testWritable.mkdirs()) {
                        testWritable.delete();
                    } else {
                        path = null;
                    }
                }
            }
            if (path != null) {
                sdCardFile = new File(path);
                return sdCardFile.getAbsolutePath();
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {

        String[] toSearch = FileUtils.readFile("/system/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * 获取mnt开头的外置sd卡路径
     *
     * @return
     */
    @SuppressLint("SdCardPath")
    public static String getExtPathOther() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        Log.d(TAG, sd_default);
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdcard_path;
    }


}
