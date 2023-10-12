package com.springboot.application.orthoapp.dto;

import com.springboot.application.orthoapp.model.SyllableType;

public class SyllableDto {
	private Long id;
	private Integer imageId;
	private String imageObjectUrl;
	private Long languageId;
	private Integer userId;
	private SyllableType syllableType;

	public SyllableDto() {
		
	}
	
	public SyllableDto(Long id, Integer imageId, String imageObjectUrl, Long languageId, Integer userId,SyllableType syllableType) {
		super();
		this.id = id;
		this.imageId = imageId;
		this.imageObjectUrl = imageObjectUrl;
		this.languageId = languageId;
		this.userId = userId;
		this.syllableType=syllableType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageObjectUrl() {
		return imageObjectUrl;
	}

	public void setImageObjectUrl(String imageObjectUrl) {
		this.imageObjectUrl = imageObjectUrl;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public SyllableType getSyllableType() {
		return syllableType;
	}

	public void setSyllableType(SyllableType syllableType) {
		this.syllableType = syllableType;
	}
	
	
	
}
