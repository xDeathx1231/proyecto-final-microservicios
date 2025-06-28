package com.ef.banner.cursos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombreCompleto;
    private String correo;
    private String rol;
    private Double saldoDisponible;
}