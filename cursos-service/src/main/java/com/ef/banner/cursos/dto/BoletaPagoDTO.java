package com.ef.banner.cursos.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoletaPagoDTO {
	private String numeroBoleta;
	private String nombreCompleto;
	private String dni;
	private LocalDate fechaNacimiento;
	private String correo;
	private String rol;
}
