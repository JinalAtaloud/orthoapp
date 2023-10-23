package com.springboot.application.orthoapp.dto;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class LanguageRequest {
	
	private String language;
	private String spokenLanguage;
	private String code;
	private String json;
	private MultipartFile file;
	
	
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSpokenLanguage() {
		return spokenLanguage;
	}
	public void setSpokenLanguage(String spokenLanguage) {
		this.spokenLanguage = spokenLanguage;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	


	


	
	
	
	
	
	
	

}
