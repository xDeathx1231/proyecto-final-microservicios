package com.ef.banner.usuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ef.banner.usuarios.model.Usuario;
import com.ef.banner.usuarios.model.Usuario.Rol;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByCorreo(String correo);

	List<Usuario> findByRol(Rol rol);

}
