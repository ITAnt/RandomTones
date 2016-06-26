package com.itant.randomtones.tool;

import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;

import com.itant.randomtones.model.Tone;

public class ToneTool {

	public static Random mRandom = new Random();
	/**
	 * 设置铃声
	 * @param path
	 */
	public static void setTone(Context context, List<Tone> tones) {
		Tone tone = tones.get(mRandom.nextInt(tones.size()));
		
		ContentValues values = new ContentValues();
	    values.put(MediaStore.MediaColumns.DATA, tone.getFileUri());
	    values.put(MediaStore.MediaColumns.TITLE, tone.getTitle());
	    values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
	    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
	    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
	    values.put(MediaStore.Audio.Media.IS_ALARM, false);
	    values.put(MediaStore.Audio.Media.IS_MUSIC, false);
	    
	    //获取系统音频文件的Uri
	    Uri uri = MediaStore.Audio.Media.getContentUriForPath(tone.getFileUri());
	    context.getContentResolver().delete(uri, MediaStore.MediaColumns.DATA + "=?", new String[] {tone.getFileUri()});
		//将文件插入系统媒体库，并获取新的Uri
		Uri newUri = context.getContentResolver().insert(uri, values);
		//设置铃声
		RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);
		
	}
}
