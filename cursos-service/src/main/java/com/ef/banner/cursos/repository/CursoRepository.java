package com.ef.banner.cursos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ef.banner.cursos.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	List<Curso> findByActivoTrue();

	List<Curso> findByProfesorId(Long profesorId);

	List<Curso> findByProfesorIdAndActivoTrue(Long profesorId);

	List<Curso> findByFechaInicioAfter(LocalDateTime fecha);

	List<Curso> findByFechaFinBefore(LocalDateTime fecha);
}