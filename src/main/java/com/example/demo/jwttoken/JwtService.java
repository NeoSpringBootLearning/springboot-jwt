package com.example.demo.jwttoken;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public static final String SECRET_KEY = "083D07D0FA8E26121049519CDE594C8ED1482D734C08F5DC8F72EA52817B702A2948EB91A8A354FFFA4C1ED1B3EF82BD1B8E7FA5E5BE7F733CF3D64D2A6FACF4";
	
	public static final long VALIDITY =  TimeUnit.MINUTES.toMillis(30);
	
	public String generateJwtToken(UserDetails userDetails) {
		
			Map<String, String> claims = new HashMap<>();
			claims.put("iss", "https://secure.manojboot.com");
			
			return Jwts.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generatSecretKey())
				.compact();  // It will be converted into String format like json as is JWT token
	}

	private SecretKey generatSecretKey() {
		
		byte decodedSecretkey[] = Base64.getDecoder().decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(decodedSecretkey);
	}

	public String extractUser(String jwt) {
		
		Claims claims = Jwts.parser()
							.verifyWith(generatSecretKey())
							.build()
							.parseSignedClaims(jwt)
							.getPayload();
		return claims.getSubject().toString();
	}
	
	public boolean isTokenValid(String jwt) {
		Claims claims = Jwts.parser()
				.verifyWith(generatSecretKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
		return claims.getExpiration().after(Date.from(Instant.now()));
	}
}
