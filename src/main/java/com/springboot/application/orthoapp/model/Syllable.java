package com.springboot.application.orthoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.application.orthoapp.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Syllable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@JsonIgnoreProperties("syllable")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="image_id", referencedColumnName= "id")
	private Images image;

	@JsonIgnoreProperties("syllable")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_id", referencedColumnName= "id")
	private User user;

	@Enumerated(EnumType.STRING)
	private SyllableType syallableType;

	
	public Syllable() {
		
	}

	public Syllable(Long id, Images image, User user, SyllableType syallableType) {
		this.id = id;
		this.image = image;
		this.user = user;
		this.syallableType = syallableType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Images getImage() {
		return image;
	}

	public void setImage(Images image) {
		this.image = image;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SyllableType getSyallableType() {
		return syallableType;
	}

	public void setSyallableType(SyllableType syallableType) {
		this.syallableType = syallableType;
	}

	
	
	

}
