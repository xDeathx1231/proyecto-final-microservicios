package com.ef.banner.cursos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    String authHeader = request.getHeader("Authorization");

	    System.out.println("🛡️ JwtFilter ejecutado. Header: " + authHeader);

	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        String token = authHeader.replace("Bearer ", "");
	        System.out.println("🔑 Token recibido: " + token);

	        if (jwtUtil.esTokenValido(token)) {
	            String correo = jwtUtil.extraerCorreo(token);
	            String rol = jwtUtil.extraerRol(token);

	            System.out.println("✅ Autenticando: " + correo + " con rol: " + rol);

	            var auth = new UsernamePasswordAuthenticationToken(
	                    correo,
	                    "Bearer " + token,  //la clave
	                    List.of(new SimpleGrantedAuthority("ROLE_" + rol))
	            );
	            SecurityContextHolder.getContext().setAuthentication(auth);
	        } else {
	            System.out.println("❌ Token inválido");
	        }
	    } else {
	        System.out.println("⚠️ Header no válido o ausente");
	    }

	    filterChain.doFilter(request, response);
	}

}
