package com.springboot.application.orthoapp.user;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.application.orthoapp.token.Token;

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
@Document(collection="user")
public class User implements UserDetails {

	@Id
	private String id;
	
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	
	private Role role;
	
	@DBRef
	private List<Token> tokens;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//Created roles, this returns list of roles
		//return List.of(new SimpleGrantedAuthority(role.name()));
		//List of authorities of its users
		return role.getAuthorities();
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		//for us is the email
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

	
}
