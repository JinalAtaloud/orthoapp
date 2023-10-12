//Step 5
package com.springboot.application.orthoapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.application.orthoapp.token.TokenRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	//5.1
	@Autowired
	private UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final TokenRepository tokenRepository;

	@Override
	
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		//Check if JWT Token exists
		//We need to pass JWT authentication in the header
		//Bearer token
		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String userEmail;
		System.out.println("Inside doFilter");
		System.out.println("Request header:"+authHeader);
		System.out.println("Response:"+response.toString());
		
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			//pass to next filter
		
			filterChain.doFilter(request, response);
			//We don't want to continue the rest of the flow in this case
			return;
		}
		
 		//extract token from header
		jwtToken = authHeader.substring(7);
		
		//Before calling the db service, we need to extract username from token
		userEmail = jwtService.extractUsername(jwtToken); //TO DO Extract user email from JWT token;
		if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() ==  null) {
			System.out.println("Inside do Filter internal");
			System.out.println("userEmail: "+userEmail);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			System.out.println("Double checking if token is valid");
			//Double check if token is valid at database side
			var isValidToken= tokenRepository.findByToken(jwtToken)
					.map(t -> !t.getExpired() && !t.getRevoked())
					.orElse(false);
					;
			System.out.println("After db call");
			//check token valid or not
			if(jwtService.isTokenValid(jwtToken, userDetails)  && isValidToken) {
				//if valid then we need to update the securityContextHolder and then send request to dispatcher servlet
				
				//This object is needed by spring and security context holder to update the DS
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				//Add more details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//update security context holder
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		//Mandatory to pass to next filter to be executed
		
		filterChain.doFilter(request, response);
		 
		
	}

}
