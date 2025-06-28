package com.ef.banner.cursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ef.banner.cursos.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    /**
     * Busca el carrito más reciente en estado PAGADO para un usuario,
     * garantizando que tenga fechaPago no nula.
     *
     * @param usuarioId ID del usuario
     * @param estado Estado del carrito (normalmente "PAGADO")
     * @return Optional con el carrito más reciente o vacío si no existe
     */
    Optional<Carrito> findTopByUsuarioIdAndEstadoIgnoreCaseAndFechaPagoIsNotNullOrderByFechaPagoDesc(Long usuarioId, String estado);
}
