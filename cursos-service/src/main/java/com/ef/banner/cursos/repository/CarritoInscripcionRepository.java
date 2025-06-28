package com.ef.banner.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ef.banner.cursos.model.CarritoInscripcion;

@Repository
public interface CarritoInscripcionRepository extends JpaRepository<CarritoInscripcion, Long> {
	Optional<CarritoInscripcion> findByUsuarioIdAndEstado(Long usuarioId, String estado);
}