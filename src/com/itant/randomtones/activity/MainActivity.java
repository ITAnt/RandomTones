package com.itant.randomtones.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.itant.randomtones.R;
import com.itant.randomtones.RandomTonesApp;
import com.itant.randomtones.adapter.RandomToneAdapter;
import com.itant.randomtones.model.Tone;
import com.itant.randomtones.tool.ToneTool;
import com.lidroid.xutils.exception.DbException;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn_clear;
	private ListView lv_random_list;
	private RandomToneAdapter mAdapter;
	
	private TextView tv_add_tones;
	
	private List<Tone> mTones;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_add_tones = (TextView) findViewById(R.id.tv_add_tones);
		tv_add_tones.setOnClickListener(this);
		
		btn_clear = (Button) findViewById(R.id.btn_clear);
		btn_clear.setOnClickListener(this);
		
		lv_random_list = (ListView) findViewById(R.id.lv_random_list);
		mAdapter = new RandomToneAdapter(this);
		mTones = new ArrayList<Tone>();
		mAdapter.setmTones(mTones);
		lv_random_list.setAdapter(mAdapter);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_add_tones:
			// 点击添加铃声到随机列表
			Intent allTonesIntent = new Intent(this, AllTonesActivity.class);
			startActivityForResult(allTonesIntent, 2);
			break;
		case R.id.btn_clear:
			// 点击清除随机列表
			new AlertDialog.Builder(this).setTitle("确定清空当前随机铃声列表？")
			.setNegativeButton("否", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					try {
						RandomTonesApp.getDbUtils().deleteAll(Tone.class);
						refreshList();
						//getContentResolver().update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			})
			.show();
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 2 && resultCode == 2) {
			
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		refreshList();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	/**
	 * 更新列表
	 */
	private void refreshList() {
		try {
			if (mTones.size() > 0) {
				mTones.clear();
			}
			
			List<Tone> tones = RandomTonesApp.getDbUtils().findAll(Tone.class);
			if (tones != null) {
				mTones.addAll(tones);
			}
			
			if (mTones.size() > 0) {
				showList();
				ToneTool.setTone(MainActivity.this, mTones);
			} else {
				hideList();
			}
			
			mAdapter.notifyDataSetChanged();
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示列表
	 */
	private void showList() {
		tv_add_tones.setVisibility(View.GONE);
		btn_clear.setVisibility(View.VISIBLE);
		lv_random_list.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 隐藏列表
	 */
	private void hideList() {
		tv_add_tones.setVisibility(View.VISIBLE);
		btn_clear.setVisibility(View.GONE);
		lv_random_list.setVisibility(View.GONE);
	}
}
