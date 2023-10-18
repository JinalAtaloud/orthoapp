package com.springboot.application.orthoapp.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

@Document(collection="video")
public class Videos {
	
	@Id
	private String id;
	private String fileUrl;
	private String title;
	private String description;
	private String lastModifiedDate;
	private String fileName;
	
	@DBRef
	private Languages language;
	
	public Languages getLanguage() {
		return language;
	}


	public void setLanguage(Languages language) {
		this.language = language;
	}


	public Videos() {

	}
	

	public Videos(String id, String fileUrl, String title, String description, String lastModifiedDate,
			String fileName, Languages language) {
		super();
		this.id = id;
		this.fileUrl = fileUrl;
		this.title = title;
		this.description = description;
		this.lastModifiedDate = lastModifiedDate;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String formatDateTime) {
		this.lastModifiedDate = formatDateTime;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	

	@Override
	public String toString() {
		return "TrainingVideoModel [id=" + id + ", fileUrl=" + fileUrl + ", title=" + title + ", description="
				+ description + ", lastModifiedDate=" + lastModifiedDate + ", fileName=" + fileName + "]";
	}

}
