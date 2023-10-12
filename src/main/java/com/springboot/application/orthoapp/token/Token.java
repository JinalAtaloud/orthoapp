package com.springboot.application.orthoapp.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.application.orthoapp.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String token;
	
	@Enumerated(EnumType.STRING)
	private TokenType tokenType;
	
	private Boolean expired;
	private Boolean revoked;
	
	@JsonIgnoreProperties("token")
	@ManyToOne()
	@JoinColumn(name = "user_id")
	private User user;
	
	
	
	

}
