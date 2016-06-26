package com.itant.randomtones;

import android.app.Application;
import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.umeng.analytics.MobclickAgent;

public class RandomTonesApp extends Application {

	private Context mContext;
	
	private static DbUtils mRandomDB;
	private final String DB_NAME = "random.db";
	private final int DB_VERSION = 1;
	
	public static DbUtils getDbUtils() {
		return mRandomDB;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mContext = getApplicationContext();
		
		mRandomDB = DbUtils.create(mContext, DB_NAME, DB_VERSION,
				new DbUpgradeListener() {
			@Override
			public void onUpgrade(DbUtils db, int oldVersion,
					int newVersion) {

			}
		});

		mRandomDB.configDebug(true);
		MobclickAgent.updateOnlineConfig(mContext);
	}
}
