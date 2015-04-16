package com.liefern;

import android.app.Application;

public class LiefernApplication extends Application {
	private static Application instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public static Application getAppContext() {
		return instance;
	}
}
