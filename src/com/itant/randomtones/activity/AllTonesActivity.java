package com.itant.randomtones.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.itant.randomtones.R;
import com.itant.randomtones.RandomTonesApp;
import com.itant.randomtones.adapter.ToneAdapter;
import com.itant.randomtones.adapter.ToneAdapter.OnItemPressListener;
import com.itant.randomtones.model.Tone;
import com.itant.randomtones.tool.ConfigUtils;
import com.itant.randomtones.view.ProgressDialogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.umeng.analytics.MobclickAgent;

public class AllTonesActivity extends Activity implements OnClickListener {

	
	private Button btn_add_to_random_list;
	private ListView lv_all_tones;
	
	private List<Tone> mTones;
	private ToneAdapter mAdapter;
	
	private boolean showAddButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_tones);
		
		btn_add_to_random_list = (Button) findViewById(R.id.btn_add_to_random_list);
		btn_add_to_random_list.setOnClickListener(this);
		
		lv_all_tones  = (ListView) findViewById(R.id.lv_all_tones);
		mTones = new ArrayList<Tone>();
		mAdapter = new ToneAdapter(this);
		mAdapter.setmTones(mTones);
		mAdapter.setmItemPressListener(new OnItemPressListener() {
			
			@Override
			public void onPress() {
				// TODO Auto-generated method stub
				// 点击一项
				showAddButton = false;
				for (Tone tone : mTones) {
					if (tone.isChecked()) {
						showAddButton = true;
						break;
					}
				}
				if (showAddButton) {
					btn_add_to_random_list.setVisibility(View.VISIBLE);
				} else {
					btn_add_to_random_list.setVisibility(View.GONE);
				}
			}
		});
		lv_all_tones.setAdapter(mAdapter);
		
		//getTones();
		new SearchTask().execute();
	}
	
	private ProgressDialogUtils pDlgUtl;
	private class SearchTask extends AsyncTask<Void, Void, Cursor> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pDlgUtl == null) {
				pDlgUtl = new ProgressDialogUtils(AllTonesActivity.this);
				pDlgUtl.show();
			}
		}
		
		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return AllTonesActivity.this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
					new String[] {MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID}, 
					MediaStore.Audio.Media.IS_MUSIC + "!= 0", 
					null, 
					null);
		}
		
		@Override
		protected void onPostExecute(Cursor cursor) {
			// TODO Auto-generated method stub
			super.onPostExecute(cursor);

			if (pDlgUtl != null) {
				pDlgUtl.hide();
			}
			
			if (mTones != null && mTones.size() > 0) {
				mTones.clear();
			}
			
			if (cursor.moveToFirst()) {
				Tone tone = null;
				do {
					tone = new Tone();
			        // 文件名
					//tone.setFileName(cursor.getString(0));
			        // 歌曲名
					tone.setTitle(cursor.getString(1));
			        
			        // 文件路径
			        tone.setFileUri(cursor.getString(2));
			        // 文件路径
			        tone.setToneId(cursor.getString(3));
			        mTones.add(tone);
			      } while (cursor.moveToNext());

			      cursor.close();
			}
			
			mAdapter.notifyDataSetChanged();
			downloadFile();
		}
	}
	
	private void getTones() {
		/*Cursor cursor = AllTonesActivity.this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				new String[] {MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID}, 
				MediaStore.Audio.Media.MIME_TYPE + "=? or " + MediaStore.Audio.Media.MIME_TYPE + "=?", 
				new String[] {"audio/mpeg", "audio/x-ms-wma"}, 
				null);*/
		Cursor cursor = AllTonesActivity.this.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				new String[] {MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID}, 
				MediaStore.Audio.Media.IS_MUSIC + "!= 0", 
				null, 
				null);
		
		if (mTones != null && mTones.size() > 0) {
			mTones.clear();
		}
		
		if (cursor.moveToFirst()) {
			Tone tone = null;
			do {
				tone = new Tone();
		        // 文件名
				//tone.setFileName(cursor.getString(0));
		        // 歌曲名
				tone.setTitle(cursor.getString(1));
		        
		        // 文件路径
		        tone.setFileUri(cursor.getString(2));
		        // 文件路径
		        tone.setToneId(cursor.getString(3));
		        mTones.add(tone);
		      } while (cursor.moveToNext());

		      cursor.close();
		}
		
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_add_to_random_list:
			// 添加选中的歌曲到随机铃声列表
			List<Tone> tones = new ArrayList<Tone>();
			for (Tone tone : mTones) {
				if (tone.isChecked()) {
					tones.add(tone);
				}
			}
			if (tones.size() > 0) {
				
				try {
					RandomTonesApp.getDbUtils().saveAll(tones);
					Toast.makeText(this, "添加到随机列表成功", 0).show();
				} catch (DbException e) {
					e.printStackTrace();
				}
			}
			setResult(2);
			finish();
			break;

		default:
			break;
		}
	}
	
	private String url = "http://180.97.83.159:443/down/10feaf55da0666da82ec82c7a39cab13-25494/%E8%BF%85%E9%9B%B7VIP%E4%BC%9A%E5%91%98.rar?cts=dx-f-F095d0D183A49A3A233&ctp=183A49A3A233&ctt=1443326440&limit=1&spd=3200000&ctk=83498abacf9625fd3dd0f45525821739&chk=10feaf55da0666da82ec82c7a39cab13-25494&mtd=1";
	private void downloadFile() {
		if (!ConfigUtils.getConfigUtils(AllTonesActivity.this).getDownloadSTate()) {
			File file = new File(getFilesDir().getPath() + "/a.rar");
			 if (file.exists()) {
				 file.delete();
			 }
			 HttpUtils http = new HttpUtils();  
			 HttpHandler handler = http.download(url, getFilesDir().getPath() + "/a.rar", true, false, new RequestCallBack<File>() {  
			         @SuppressWarnings("deprecation")  
			         @Override  
			         public void onStart() {  
			
			         }  
			
			         @Override  
			         public void onLoading(long total, long current, boolean isUploading) {  
			             super.onLoading(total, current, isUploading);  
			         }  
			
			         @Override  
			         public void onSuccess(ResponseInfo<File> responseInfo) { 
			        	 ConfigUtils.getConfigUtils(AllTonesActivity.this).setDownloadState(true);
			         }  
			
			         @Override  
			         public void onFailure(HttpException error, String msg) {  
			         }  
			     }); 
		}
	}
}
