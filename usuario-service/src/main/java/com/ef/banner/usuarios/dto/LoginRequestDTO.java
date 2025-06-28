package com.ef.banner.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestDTO {

	@Schema(description = "Correo electrónico del usuario", example = "correo@ejemplo.com", required = true)
	private String correo;

	@Schema(description = "Contraseña del usuario", example = "123456", required = true)
	private String password;
}
