package com.hb.rimi.angel.util;

import android.content.Context;
import android.widget.Toast;

/**
 * toast 统一管理类
 * 
 * @author ouyangbin
 */
public class T {
	private static Toast t;
	private static int time = Toast.LENGTH_LONG;

	private T() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 显示toast
	 * 
	 * @param c
	 * @param s
	 */
	public static void ShowToast(Context c, String s) {
		if (t == null) {
			t = Toast.makeText(c, s, time);
		} else {
			t.setText(s);
		}
		t.show();
	}

	/**
	 * 自定义时间显示toast
	 * 
	 * @param c
	 * @param s
	 * @param time
	 */
	public static void ShowToast(Context c, String s, int time) {
		if (t == null) {
			if (time == 0) {
				t = Toast.makeText(c, s, Toast.LENGTH_SHORT);
			} else if (time == 1) {
				t = Toast.makeText(c, s, Toast.LENGTH_LONG);
			}
		} else {
			t.setText(s);
		}
		if (time == 0) {
			t.setDuration(Toast.LENGTH_SHORT);
		} else if (time == 1) {
			t.setDuration(Toast.LENGTH_LONG);
		}
		t.show();
	}
}
