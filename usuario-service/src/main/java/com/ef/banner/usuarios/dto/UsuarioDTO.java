package com.ef.banner.usuarios.dto;

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
	private Double saldoDisponible;
}
