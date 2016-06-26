package com.itant.randomtones.tool;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigUtils {
	private static ConfigUtils mConfigUtils = null;
	private SharedPreferences mPreferences = null;
	
	private ConfigUtils(Context context) {
		mPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}
	
	private static synchronized void initInstance(Context context) {
		if (mConfigUtils == null) {
			mConfigUtils = new ConfigUtils(context);
		}
	}
	
	public static ConfigUtils getConfigUtils(Context context) {
		if (mConfigUtils == null) {
			initInstance(context);
		}
		
		return mConfigUtils;
	}
	
	/**
	 * 保存上一次播放的视频名字
	 * @param name
	 */
	public void saveLastPlayName(String name) {
		mPreferences.edit().putString("last", name).commit();
	}
	
	public String getLastPlayName() {
		return mPreferences.getString("last", "无");
	}
	
	public void setDownloadState(boolean status) {
		mPreferences.edit().putBoolean("status", status).commit();
	}
	
	public boolean getDownloadSTate() {
		return mPreferences.getBoolean("status", false);
	}
}
