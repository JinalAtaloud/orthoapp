package com.springboot.application.orthoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity()
public class Images {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private String fileUrl;
	private String fileName;
	
	@JsonIgnoreProperties("images")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="language_id", referencedColumnName= "id")
	private Languages language;
	
	
	public Images() {
		
	}


	public Images(Integer id, String fileUrl, String fileName, Languages language) {
		super();
		this.id = id;
		this.fileUrl = fileUrl;
		this.fileName = fileName;
		this.language = language;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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

	

	public Languages getLanguage() {
		return language;
	}


	public void setLanguage(Languages language) {
		this.language = language;
	}



	

}
