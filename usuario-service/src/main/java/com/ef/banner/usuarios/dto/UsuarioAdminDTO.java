package com.ef.banner.usuarios.dto;

import java.time.LocalDate;

import com.ef.banner.usuarios.model.Usuario.Rol;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para que el ADMIN edite completamente los datos de un usuario.")
public class UsuarioAdminDTO {

	@Schema(description = "Nombre completo del usuario", example = "María López")
	private String nombreCompleto;

	@Schema(description = "Correo electrónico del usuario", example = "maria.lopez@mail.com")
	private String correo;

	@Schema(description = "Teléfono del usuario", example = "987654321")
	private String telefono;

	@Schema(description = "Dirección del usuario", example = "Av. Los Álamos 456, Lima")
	private String direccion;

	@Schema(description = "Fecha de nacimiento del usuario", example = "2000-04-12")
	private LocalDate fechaNacimiento;

	@Schema(description = "Rol del usuario (ADMIN, ESTUDIANTE, PROFESOR)", example = "ESTUDIANTE")
	private Rol rol;
}
