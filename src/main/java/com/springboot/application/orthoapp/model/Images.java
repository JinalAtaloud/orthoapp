package com.springboot.application.orthoapp.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

@Document(collection="image")
public class Images {
	
	@Id
	private String id;
	private String fileUrl;
	private String fileName;
	
	@DBRef
	private Language language;
	
	
	public Images() {
		
	}


	public Images(String id, String fileUrl, String fileName, Language language) {
		super();
		this.id = id;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.language = language;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	public Language getLanguage() {
		return language;
	}


	public void setLanguage(Language language) {
		this.language = language;
	}



	

}
