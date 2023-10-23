package com.springboot.application.orthoapp.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.application.orthoapp.config.JwtService;
import com.springboot.application.orthoapp.token.Token;
import com.springboot.application.orthoapp.token.TokenRepository;
import com.springboot.application.orthoapp.token.TokenType;
import com.springboot.application.orthoapp.user.User;
import com.springboot.application.orthoapp.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	// 7.1
	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				//.role(Role.USER)
				.build();
		
		var savedUser = repository.save(user);
		var jwtToken = jwtService.generateToken(user);
		//var refreshToken = jwtService.generateRefreshToken(user);
		
		saveUserToken(savedUser,jwtToken);
		
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.build();

	}

	// 7.2
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword())
				);
		var user = repository.findByEmail(request.getEmail()).orElseThrow();

		var jwtToken = jwtService.generateToken(user);
		//var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);
		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.build();
	}
	
	private void revokeAllUserTokens(User user) {
		var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
		if(validUserToken.isEmpty()) {
			return;
		}
		
		validUserToken.forEach(t -> {
			t.setExpired(true);
			t.setRevoked(true);
			});
		
		tokenRepository.saveAll(validUserToken);
	}

	private void saveUserToken(User user, String jwtToken) {
	    var token = Token.builder()
	        .user(user)
	        .token(jwtToken)
	        .tokenType(TokenType.BEARER)
	        .expired(false)
	        .revoked(false)
	        .build();
	    tokenRepository.save(token);
	  }

}

