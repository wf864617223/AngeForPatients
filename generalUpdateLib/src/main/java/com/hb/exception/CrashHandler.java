package com.hb.exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 异常捕获类
 * 
 * @author TXG
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {
	private static CrashHandler instance; // 单例引用，这里我们做成单例的，因为我们一个应用程序里面只需要一个UncaughtExceptionHandler实例
	// 程序的Context对象
	private Context mContext;
	// 系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	public static final String TAG = CrashHandler.class.getSimpleName();

	public CrashHandler() {
	}

	public synchronized static CrashHandler getInstance() { // 同步方法，以免单例多线程环境下出现异常
		if (instance == null) {
			instance = new CrashHandler();
		}
		return instance;
	}

	public void init(Context mContext) {

		this.mContext = mContext;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 初始化，把当前对象设置成UncaughtExceptionHandler处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) { // 当有未处理的异常发生时，就会来到这里。。
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}

			// 退出程序
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		// 使用Toast来显示异常信息
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			}
		}.start();
		// 保存日志文件
		writeLog(ex);
		return true;
	}

	// 写入Log信息的方法，写入到SD卡里面
	private void writeLog(Throwable ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		// 获取跟踪的栈信息，除了系统栈信息，还把手机型号、系统版本、编译版本的唯一标示
		// 追加信息，因为后面会回调默认的处理方法
		ex.printStackTrace(printWriter);
		// 把上面获取的堆栈信息转为字符串，打印出来
		String stacktrace = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
				.format(new Date())
				+ "\r\nRELEASE:\r\n"
				+ android.os.Build.VERSION.RELEASE
				+ "\r\nMODEL:\r\n"
				+ android.os.Build.MODEL + "\r\n" + result.toString();
		printWriter.close();
		// 这里把刚才异常堆栈信息写入SD卡的Log日志里面
		String sdcardPath = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			sdcardPath = Environment.getExternalStorageDirectory().getPath();

			try {
				File destDir = new File(sdcardPath + "/generalupdate");
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
				String filename = sdcardPath + "/generalupdate/generalupdate_err.log";
				FileOutputStream stream = new FileOutputStream(filename, true);
				OutputStreamWriter output = new OutputStreamWriter(stream);
				BufferedWriter bw = new BufferedWriter(output);
				// 写入相关Log到文件
				bw.write(stacktrace);
				bw.newLine();
				bw.close();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
