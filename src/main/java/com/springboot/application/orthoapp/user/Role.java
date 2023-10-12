//Step 2
package com.springboot.application.orthoapp.user;

import static com.springboot.application.orthoapp.user.Permission.ADMIN_CREATE;
import static com.springboot.application.orthoapp.user.Permission.ADMIN_DELETE;
import static com.springboot.application.orthoapp.user.Permission.ADMIN_READ;
import static com.springboot.application.orthoapp.user.Permission.ADMIN_UPDATE;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_CREATE;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_DELETE;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_READ;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_UPDATE;
import static com.springboot.application.orthoapp.user.Permission.USER_UPDATE;
import static com.springboot.application.orthoapp.user.Permission.USER_WRITE;
import static com.springboot.application.orthoapp.user.Permission.USER_READ;
import static com.springboot.application.orthoapp.user.Permission.USER_DELETE;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

	USER(Set.of(USER_READ)),
	ADMIN(Set.of(ADMIN_READ, ADMIN_CREATE, ADMIN_DELETE, ADMIN_UPDATE)),
	MANAGER(Set.of(MANAGER_READ, MANAGER_CREATE, MANAGER_DELETE, MANAGER_UPDATE));

	@Getter
	private final Set<Permission> permissions;
	
	 public List<SimpleGrantedAuthority> getAuthorities() {
		    var authorities = getPermissions()
		            .stream()
		            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
		            .collect(Collectors.toList());
		    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		    return authorities;
		  }
	
//	public List<SimpleGrantedAuthority> getAuthorities(){
//		var authorities = getPermissions()
//		.stream()
//		//.map(permission -> new SimpleGrantedAuthority(permission.name()))
//		.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//		.collect(Collectors.toList());
//		//ROLE.getAuthorities ->ADMIN->this.name(role)
//		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//		return authorities;
//	}
}
