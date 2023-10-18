package com.springboot.application.orthoapp.dto;

import com.springboot.application.orthoapp.model.SyllableType;

public class SyllableDto {
	private String id;
	private String imageId;
	private String imageObjectUrl;
	private String languageId;
	private String userId;
	private SyllableType syllableType;

	public SyllableDto() {
		
	}
	
	public SyllableDto(String id, String imageId, String imageObjectUrl, String languageId, String userId,SyllableType syllableType) {
		super();
		this.id = id;
		this.imageId = imageId;
		this.imageObjectUrl = imageObjectUrl;
		this.languageId = languageId;
		this.userId = userId;
		this.syllableType=syllableType;
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getImageObjectUrl() {
		return imageObjectUrl;
	}

	public void setImageObjectUrl(String imageObjectUrl) {
		this.imageObjectUrl = imageObjectUrl;
	}

	public String getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public SyllableType getSyllableType() {
		return syllableType;
	}

	public void setSyllableType(SyllableType syllableType) {
		this.syllableType = syllableType;
	}
	
	
	
}
