package com.springboot.application.orthoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Videos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String fileUrl;
	private String title;
	private String description;
	private String lastModifiedDate;
	private String fileName;
	
	@JsonIgnoreProperties("videos")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="language_id", referencedColumnName= "id")
	private Languages language;


	

	public Languages getLanguage() {
		return language;
	}


	public void setLanguage(Languages language) {
		this.language = language;
	}


	public Videos() {

	}
	

	public Videos(Long id, String fileUrl, String title, String description, String lastModifiedDate,
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





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
