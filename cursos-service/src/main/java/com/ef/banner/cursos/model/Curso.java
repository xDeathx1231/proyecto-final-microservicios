package com.ef.banner.cursos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "profesor_id", nullable = false)
    private Long profesorId;

    @Column(name = "horario_propuesto")
    private String horarioPropuesto;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "duracion_horas")
    private Integer duracionHoras;

    /** ✅ Relación con la categoría */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Categoria categoria;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private Integer vacantes;

    @Column(name = "vacantes_disponibles", nullable = false)
    private Integer vacantesDisponibles;

    @Column(nullable = false)
    @Min(value = 1, message = "El número mínimo de créditos es 1")
    @Max(value = 5, message = "El número máximo de créditos es 5")
    private Integer creditos;

}