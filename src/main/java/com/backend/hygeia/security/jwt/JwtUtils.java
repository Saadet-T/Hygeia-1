package com.backend.hygeia.security.jwt;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.backend.hygeia.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LogManager.getLogger(AuthTokenFilter.class);

	@Value("${hygeia.app.jwtSecret}")
	private String jwtSecret;

	@Value("${hygeia.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return JWT.create().withSubject((userPrincipal.getUsername()))
				.withExpiresAt(new Date((new Date()).getTime() + jwtExpirationMs)).withIssuer("hygeia")
				.sign(Algorithm.HMAC512(jwtSecret.getBytes()));
	}

	public String getUserNameFromJwtToken(String token) {
		return JWT.decode(token).getSubject();
		// return
		// Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();

	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e);
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e);
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e);
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e);
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e);
		}

		return false;
	}
}
