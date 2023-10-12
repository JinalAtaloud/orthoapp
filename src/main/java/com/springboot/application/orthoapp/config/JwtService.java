//Step 6
package com.springboot.application.orthoapp.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${application.security.jwt.secret}")
	private String SECRET_KEY;
	
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	
	@Value("${application.security.jwt.refresh-token.expiration}")
	private long refreshExpiration;

	// 1
	public String extractUsername(String token) {
		// 5
		// subject is username/email
		return extractClaim(token, Claims::getSubject);

	}

	// Extract all claims
	// 2
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	// 3
	// returns a key
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// 4
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);

	}
	
	//5
	// to pass any other information using extra claims
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails,jwtExpiration);

	}

	
//	public String generateRefreshToken(UserDetails userDetails) {
//		return buildToken(new HashMap<>(), userDetails,refreshExpiration);
//
//	}

	
	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails,long expiration) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				// 24 hours + 1000 ms
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	//6
	// token only from user details , no extra claims
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	// 7
	// To check if user exists from db and token belongs to this user
	public Boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//8
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());

	}

	//9
	private java.util.Date extractExpiration(String token) {

		return extractClaim(token, Claims::getExpiration);
	}

}
