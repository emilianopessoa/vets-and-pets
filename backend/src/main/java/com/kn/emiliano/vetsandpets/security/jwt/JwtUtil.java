package com.kn.emiliano.vetsandpets.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.kn.emiliano.vetsandpets.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Utility class to manage JWT tokens for authentication.
 * 
 * @author Emiliano Pessoa
 * 
 */
@Component
public class JwtUtil {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.expiration}")
	private int jwtExpiration;

	/**
	 * Generates a JWT token using the given authentication data.
	 *
	 * @param authentication the authentication object
	 * @return the JWT token as a string
	 */
	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * Generates a JWT token using the given authentication data, secret, and
	 * expiration time.
	 *
	 * @param authentication the authentication object
	 * @param jwtSecret      the JWT secret
	 * @param jwtExpiration  the JWT expiration time
	 * @return the JWT token as a string
	 */
	public String generateJwtToken(Authentication authentication, String jwtSecret, int jwtExpiration) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiration))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/**
	 * Extracts the username from the given JWT token.
	 *
	 * @param token the JWT token
	 * @return the username extracted from the token
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * Validates the given JWT token by checking its signature and other properties.
	 *
	 * @param authToken the JWT token to validate
	 * @return true if the token is valid, false otherwise
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
