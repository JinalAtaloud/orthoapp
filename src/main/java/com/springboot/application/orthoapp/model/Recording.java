package com.springboot.application.orthoapp.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Recording {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String fileObjectUrl;
	private String filename;
	private Date createdDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "referenceList", "handler", "hibernateLazyInitializer" }, allowSetters = true)
	@JoinColumn(name = "language_id", referencedColumnName = "id")
	private Languages language;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = { "referenceList", "handler", "hibernateLazyInitializer" }, allowSetters = true)
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Images image;

	public Recording() {
	}

	public Recording(Long id, String fileObjectUrl, String filename, Date createdDate, Languages language,
			Images image) {
		this.id = id;
		this.fileObjectUrl = fileObjectUrl;
		this.filename = filename;
		this.createdDate = createdDate;
		this.language = language;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}

	public Languages getLanguage() {
		return language;
	}

	public void setLanguage(Languages language) {
		this.language = language;
	}

	public Images getImage() {
		return image;
	}

	public void setImage(Images image) {
		this.image = image;
	}

}
