package com.itant.randomtones.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.itant.randomtones.RandomTonesApp;
import com.itant.randomtones.model.Tone;
import com.itant.randomtones.tool.ToneTool;
import com.lidroid.xutils.exception.DbException;

public class PhoneOffReceiver extends BroadcastReceiver {

	private List<Tone> mTones;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		try {
			mTones = RandomTonesApp.getDbUtils().findAll(Tone.class);
			if (mTones != null && mTones.size() > 0) {
				ToneTool.setTone(context, mTones);
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
