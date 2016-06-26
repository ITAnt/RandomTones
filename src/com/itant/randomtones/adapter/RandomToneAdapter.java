package com.itant.randomtones.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.itant.randomtones.R;
import com.itant.randomtones.model.Tone;

public class RandomToneAdapter extends BaseAdapter {

	private List<Tone> mTones;
	private Context mContext;
	private ViewHolder mViewHolder;
	
	public void setmTones(List<Tone> mTones) {
		this.mTones = mTones;
	}



	public RandomToneAdapter(Context context) {
		this.mContext = context;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mTones != null) {
			return mTones.size();
		}
		return 0;
	}



	@Override
	public Tone getItem(int position) {
		// TODO Auto-generated method stub
		if (mTones != null) {
			return mTones.get(position);
		}
		return null;
	}



	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_random_tone_list, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tv_tone = (TextView) convertView.findViewById(R.id.tv_tone);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		final Tone tone = mTones.get(position);
		mViewHolder.tv_tone.setText(tone.getTitle());
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tv_tone;
	}
}
