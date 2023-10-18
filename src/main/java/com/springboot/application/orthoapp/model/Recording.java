package com.springboot.application.orthoapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="recording")
public class Recording {

	@Id
	private String id;
	private String fileObjectUrl;
	private String filename;
	private String createdDate;

	@DBRef
	private Images image;

	public Recording() {
	}

	public Recording(String id, String fileObjectUrl, String filename, String createdDate,
			Images image) {
		this.id = id;
		this.fileObjectUrl = fileObjectUrl;
		this.filename = filename;
		this.createdDate = createdDate;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileObjectUrl() {
		return fileObjectUrl;
	}

	public void setFileObjectUrl(String fileObjectUrl) {
		this.fileObjectUrl = fileObjectUrl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String date) {
		this.createdDate = date;
	}

	public Images getImage() {
		return image;
	}

	public void setImage(Images image) {
		this.image = image;
	}

}
