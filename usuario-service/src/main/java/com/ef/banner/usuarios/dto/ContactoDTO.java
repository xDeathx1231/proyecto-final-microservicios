package com.ef.banner.usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO utilizado para actualizar los datos de contacto del usuario.")
public class ContactoDTO {

	@Schema(description = "Nuevo correo electrónico del usuario", example = "nuevo.correo@mail.com")
	private String correo;

	@Schema(description = "Nuevo número de teléfono del usuario", example = "987654321")
	private String telefono;

	@Schema(description = "Nueva dirección del usuario", example = "Calle Falsa 123, Surco")
	private String direccion;
}
