package com.ef.banner.cursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ef.banner.cursos.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
	List<Pago> findByUsuarioId(Long usuarioId);
}
