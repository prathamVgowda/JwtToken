package com.example.demo.config;

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

	private static final Integer expaireTime = 1000 * 60 * 60 * 3;

	private final static String SECRATE_KEY = "6A576D5A7134743777217A25432A462D4A614E645267556B58703272357538782F413F4428472B4B6250655368566D5971337436763979244226452948404D63";

	// this for filter or validatating token

	public String separatingUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public Date extractExpration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);

	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Boolean isTokenExpired(String token) {
		// comparing
		return extractExpration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = separatingUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// this is for genarating the token

	public String genarateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	public String createToken(Map<String, Object> claims, String username) {

		// setting the whole jwt
		return Jwts.builder().setClaims(claims)
				// setting the username or payload
				.setSubject(username)
				// setting the issued time
				.setIssuedAt(new Date(System.currentTimeMillis()))
				// setting the expiretime
				.setExpiration(new Date(System.currentTimeMillis() + expaireTime))
				// setting the signature
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRATE_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
