package com.springboot.application.orthoapp.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.springboot.application.orthoapp.user.User;

import org.springframework.data.annotation.Id;

@Document(collection="syllable")
public class Syllable {
	
	@Id
	private String id;

	@DBRef
	private Images image;

	@DBRef
	private User user;

	private SyllableType syallableType;

	
	public Syllable() {
		
	}

	public Syllable(String id, Images image, User user, SyllableType syallableType) {
		this.id = id;
		this.image = image;
		this.user = user;
		this.syallableType = syallableType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
