package com.ef.banner.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ef.banner.cursos.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	boolean existsByNombreIgnoreCase(String nombre);
}
