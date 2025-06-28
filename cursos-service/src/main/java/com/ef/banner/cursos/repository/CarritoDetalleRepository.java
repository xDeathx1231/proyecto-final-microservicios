package com.ef.banner.cursos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ef.banner.cursos.model.CarritoDetalle;

@Repository
public interface CarritoDetalleRepository extends JpaRepository<CarritoDetalle, Long> {
	List<CarritoDetalle> findByCarritoId(Long carritoId);
}