package com.itant.randomtones.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;

public class Tone {

	@Id(column="ID")
	private int ID;
	@Column(column="toneId")
	private String toneId;
	@Column(column="title")
	private String title;
	@Column(column="isChecked")
	private boolean isChecked;
	@Column(column="fileUri")
	private String fileUri;

	
	public String getToneId() {
		return toneId;
	}

	public void setToneId(String toneId) {
		this.toneId = toneId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}
}
