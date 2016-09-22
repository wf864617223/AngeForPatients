package com.hb.exception;

import android.app.Application;

public class CashApplication extends Application {
	private static CashApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler.getInstance().init(getApplicationContext());
	}

	public static CashApplication getInstance() {
		if (instance == null) {
			instance = new CashApplication();
		}
		return instance;
	}
}