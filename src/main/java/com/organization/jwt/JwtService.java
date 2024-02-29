package com.organization.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	// Token Creation
	public String Secret = "ghoiuyxcvbpoiufvbnmlescvghjkookjsdfgoiuysdfgoiuysdfkjhxcvbnh";

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] decode = Decoders.BASE64.decode(Secret);
		return Keys.hmacShaKeyFor(decode);
	}

	// Token Validation
	public String extractUsername(String username) {
		return extractClaims(username, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);

	}

	private <T> T extractClaims(String token, Function<Claims, T> solveClaims) {
		final Claims allClaims = extractAllClaims(token);
		return solveClaims.apply(allClaims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean validateToken(String token, UserDetails details) {
		String username = extractUsername(token);
		return (username.equals(details.getUsername()) && !isTokenExpired(token));
	}

}
