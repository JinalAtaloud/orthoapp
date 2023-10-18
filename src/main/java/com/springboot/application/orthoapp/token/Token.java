package com.springboot.application.orthoapp.token;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.springboot.application.orthoapp.user.User;

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
@Document(collection="token")
public class Token {
	
	@Id
	private String id;
	
	private String token;
	
	private TokenType tokenType;
	
	private Boolean expired;
	private Boolean revoked;
	
	@DBRef
	private User user;
	
	
	
	

}
