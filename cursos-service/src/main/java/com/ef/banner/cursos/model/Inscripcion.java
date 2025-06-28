package com.ef.banner.cursos.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad que representa la inscripción de un usuario a un curso.")
public class Inscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la inscripción", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "ID del usuario que se inscribe", example = "5")
    private Long usuarioId;

    @Column(nullable = false)
    @Schema(description = "ID del curso al que se inscribe", example = "10")
    private Long cursoId;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "Fecha y hora de la inscripción", example = "2025-06-26T19:30:00")
    private LocalDateTime fechaInscripcion;

    @Column(nullable = false)
    @Schema(description = "Estado de la inscripción (activa o no)", example = "true")
    private Boolean activo = true;
}
