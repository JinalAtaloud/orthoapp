//STEP 6 - BINDING EVERYTHING
package com.springboot.application.orthoapp.config;

import static com.springboot.application.orthoapp.user.Permission.ADMIN_CREATE;
import static com.springboot.application.orthoapp.user.Permission.ADMIN_DELETE;
import static com.springboot.application.orthoapp.user.Permission.ADMIN_READ;
import static com.springboot.application.orthoapp.user.Permission.ADMIN_UPDATE;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_CREATE;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_DELETE;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_READ;
import static com.springboot.application.orthoapp.user.Permission.MANAGER_UPDATE;
import static com.springboot.application.orthoapp.user.Permission.USER_READ;
import static com.springboot.application.orthoapp.user.Role.ADMIN;
import static com.springboot.application.orthoapp.user.Role.MANAGER;
import static com.springboot.application.orthoapp.user.Role.USER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import lombok.RequiredArgsConstructor;

//The below 2 needs to be annotated when we are dealing with SB3
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
		
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;

	
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
	    return new MvcRequestMatcher.Builder(introspector);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		 .authorizeHttpRequests(auth -> auth
		            .requestMatchers(mvc.pattern("/api/v1/auth/**")).permitAll()
		            .requestMatchers(mvc.pattern(HttpMethod.GET,"/api/v1/**")).permitAll()
		            .requestMatchers(mvc.pattern(HttpMethod.POST,"/api/v1/record/**")).permitAll()
		            .requestMatchers(mvc.pattern(HttpMethod.POST,"/api/v1/syllable/**")).permitAll()
		            .requestMatchers(mvc.pattern(HttpMethod.GET,"/api/v1/syllable/**")).permitAll()
		            
		            .requestMatchers(mvc.pattern("/api/v1/video/**")).hasRole(ADMIN.name())
		            .requestMatchers(mvc.pattern(HttpMethod.POST,"/api/v1/video/**")).hasAuthority(ADMIN_CREATE.name())
		            .requestMatchers(mvc.pattern(HttpMethod.PUT,"/api/v1/video/**")).hasAuthority(ADMIN_UPDATE.name())
		            .requestMatchers(mvc.pattern(HttpMethod.DELETE,"/api/v1/video/**")).hasAuthority(ADMIN_DELETE.name())
		            
		            .requestMatchers(mvc.pattern("/api/v1/image/**")).hasRole(ADMIN.name())
		            .requestMatchers(mvc.pattern(HttpMethod.POST,"/api/v1/image/**")).hasAuthority(ADMIN_CREATE.name())
		            .requestMatchers(mvc.pattern(HttpMethod.PUT,"/api/v1/image/**")).hasAuthority(ADMIN_UPDATE.name())
		            .requestMatchers(mvc.pattern(HttpMethod.DELETE,"/api/v1/image/**")).hasAuthority(ADMIN_DELETE.name())
		            
		            .requestMatchers(mvc.pattern("/api/v1/language/**")).hasRole(ADMIN.name())
		            .requestMatchers(mvc.pattern(HttpMethod.POST,"/api/v1/language/**")).hasAuthority(ADMIN_CREATE.name())
		            .requestMatchers(mvc.pattern(HttpMethod.PUT,"/api/v1/language/**")).hasAuthority(ADMIN_UPDATE.name())
		            .requestMatchers(mvc.pattern(HttpMethod.DELETE,"/api/v1/language/**")).hasAuthority(ADMIN_DELETE.name())
		            
		
		            .anyRequest().authenticated())
		 .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		//6.1
		 .authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
		.logout()
		.logoutUrl("/api/v1/auth/logout")
		.addLogoutHandler(logoutHandler)
		.logoutSuccessHandler((request, response , authentication) -> 
		SecurityContextHolder.clearContext()
		);
		return http.build();
		
	}
	
	

}
