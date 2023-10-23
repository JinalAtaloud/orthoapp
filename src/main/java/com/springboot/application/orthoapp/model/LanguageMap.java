package com.springboot.application.orthoapp.model;

import java.util.Map;

public class LanguageMap {
	private Map<String,String> data;
	
	public LanguageMap() {
		
	}

	public LanguageMap(Map<String, String> data) {
		this.data = data;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	
}
