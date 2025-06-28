package com.ef.banner.usuarios.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ef.banner.usuarios.model.Usuario;

public class UsuarioPrincipal implements UserDetails {

	private final Long id;
	private final String correo;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public UsuarioPrincipal(Usuario usuario) {
		this.id = usuario.getId();
		this.correo = usuario.getCorreo();
		this.password = usuario.getPassword();
		this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return correo;
	}

	@Override public boolean isAccountNonExpired() { return true; }
	@Override public boolean isAccountNonLocked() { return true; }
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }
}