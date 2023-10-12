package com.springboot.application.orthoapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AdditionalDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String spokenLanguage;
	private String symbolUrl;
	private String symbolFilename;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="language_id", referencedColumnName= "id")
	private Languages language;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpokenLanguage() {
		return spokenLanguage;
	}

	public void setSpokenLanguage(String spokenLanguage) {
		this.spokenLanguage = spokenLanguage;
	}

	public String getSymbolUrl() {
		return symbolUrl;
	}

	public void setSymbolUrl(String symbolUrl) {
		this.symbolUrl = symbolUrl;
	}

	public String getSymbolFilename() {
		return symbolFilename;
	}

	public void setSymbolFilename(String symbolFilename) {
		this.symbolFilename = symbolFilename;
	}

	public Languages getLanguage() {
		return language;
	}

	public void setLanguage(Languages language) {
		this.language = language;
	}

	
	
	

}
