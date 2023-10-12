package com.springboot.application.orthoapp.model;

import java.util.Map;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Languages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String language;
	private String code;


	@ElementCollection
	private Map<String,String> data;
	
	public Languages() {
		
	}

	public Languages(Long id, String language, String code,Map<String, String> data) {
		super();
		this.id = id;
		this.language = language;
		this.code=code;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "Languages [id=" + id + ", language=" + language + ", data=" + data + "]";
	}
	
	
	
	
	
	
	

}
