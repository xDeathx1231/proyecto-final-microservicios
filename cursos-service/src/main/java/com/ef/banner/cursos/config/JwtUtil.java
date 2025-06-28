package com.ef.banner.cursos.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	private final String SECRET_KEY = "mi_clave_super_secreta_para_token_jwt_seguro123"; // >=32 caracteres

	private Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); 
	}

	public String extraerCorreo(String token) {
		return extractClaims(token).getSubject();
	}

	public String extraerRol(String token) {
		return extractClaims(token).get("rol", String.class);
	}

	public boolean esTokenValido(String token) {
		try {
			Claims claims = extractClaims(token);
			Date now = new Date();
			return claims.getExpiration().after(now);
		} catch (Exception e) {
			System.out.println("❌ Token inválido: " + e.getMessage());
			return false;
		}
	}
}
