package com.ef.banner.cursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ef.banner.cursos.model.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
	List<Inscripcion> findByUsuarioId(Long usuarioId);

	List<Inscripcion> findByCursoId(Long cursoId);

	List<Inscripcion> findByActivoTrue();

	List<Inscripcion> findByCursoIdAndActivoTrue(Long cursoId);

	boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);
	
	boolean existsByUsuarioIdAndCursoIdAndActivoTrue(Long usuarioId, Long cursoId);

}