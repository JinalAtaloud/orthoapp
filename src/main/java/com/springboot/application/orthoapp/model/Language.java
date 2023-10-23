package com.springboot.application.orthoapp.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="language")
public class Language {
	
	@Id
	private String id;
	private String language;
	private String spokenLanguage;
	private String symbolFileUrl;
	private String symbolFilename;
	private String code;
	private Map<String,String> data;
	
	public Language() {
		
	}


	public Language(String id, String language,String spokenLanguage,String symbolFileUrl,String symbolFilename,String code, Map<String, String> data) {
		this.id = id;
		this.language = language;
		this.spokenLanguage = spokenLanguage;
		this.symbolFileUrl = symbolFileUrl;
		this.symbolFilename = symbolFilename;
		this.code = code;
		this.data = data;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public String getSpokenLanguage() {
		return spokenLanguage;
	}


	public void setSpokenLanguage(String spokenLanguage) {
		this.spokenLanguage = spokenLanguage;
	}


	public String getSymbolFileUrl() {
		return symbolFileUrl;
	}


	public void setSymbolFileUrl(String symbolFileUrl) {
		this.symbolFileUrl = symbolFileUrl;
	}


	public String getSymbolFilename() {
		return symbolFilename;
	}


	public void setSymbolFilename(String symbolFilename) {
		this.symbolFilename = symbolFilename;
	}


	@Override
	public String toString() {
		return "Languages [id=" + id + ", language=" + language + ", spokenLanguage=" + spokenLanguage
				+ ", symbolFileUrl=" + symbolFileUrl + ", symbolFilename=" + symbolFilename + ", code=" + code
				+ ", data=" + data + "]";
	}


	


	
	
	
	
	
	
	

}
