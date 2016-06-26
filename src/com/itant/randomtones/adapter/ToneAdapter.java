package com.itant.randomtones.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itant.randomtones.R;
import com.itant.randomtones.model.Tone;

public class ToneAdapter extends BaseAdapter {

	private List<Tone> mTones;
	private Context mContext;
	private ViewHolder mViewHolder;
	
	public void setmTones(List<Tone> mTones) {
		this.mTones = mTones;
	}



	public ToneAdapter(Context context) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tone_list, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tv_tone = (TextView) convertView.findViewById(R.id.tv_tone);
			mViewHolder.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
			mViewHolder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		final Tone tone = mTones.get(position);
		mViewHolder.tv_tone.setText(tone.getTitle());
		
		// 点击一项
		mViewHolder.ll_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tone.setChecked(!tone.isChecked());
				notifyDataSetChanged();
				if (mItemPressListener != null) {
					mItemPressListener.onPress();
				}
			}
		});
		if (tone.isChecked()) {
			mViewHolder.ll_item.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			mViewHolder.iv_select.setVisibility(View.VISIBLE);
		} else {
			mViewHolder.ll_item.setBackgroundColor(mContext.getResources().getColor(R.color.blue_very_light));
			mViewHolder.iv_select.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tv_tone;
		ImageView iv_select;
		LinearLayout ll_item;
	}
	
	public interface OnItemPressListener {
		void onPress();
	}
	
	private OnItemPressListener mItemPressListener;

	public void setmItemPressListener(OnItemPressListener mItemPressListener) {
		this.mItemPressListener = mItemPressListener;
	}
	
}
