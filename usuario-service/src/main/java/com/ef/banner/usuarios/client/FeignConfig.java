package com.ef.banner.usuarios.client;

import feign.RequestInterceptor;
import jakarta.ws.rs.core.HttpHeaders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

	@Bean
	public RequestInterceptor authInterceptor() {
		return template -> {
			var context = SecurityContextHolder.getContext();
			if (context != null && context.getAuthentication() != null) {
				Object credentials = context.getAuthentication().getCredentials();
				if (credentials instanceof String token && token.startsWith("Bearer ")) {
					template.header(HttpHeaders.AUTHORIZATION, token);
				}
			}
		};
	}
}
