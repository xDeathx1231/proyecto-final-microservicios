// ✅ InscripcionController.java
package com.ef.banner.cursos.controller;

import com.ef.banner.cursos.model.Inscripcion;
import com.ef.banner.cursos.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "*")
@Tag(name = "Inscripciones", description = "Operaciones relacionadas con la inscripción de usuarios a cursos")
public class InscripcionController {

	@Autowired
	private InscripcionService inscripcionService;

	@Operation(summary = "Inscribirse en un curso", description = "Registra una nueva inscripción del usuario al curso especificado, si no está previamente inscrito.")
	@PostMapping("/curso/{cursoId}/usuario/{usuarioId}")
	public ResponseEntity<?> inscribir(@PathVariable Long usuarioId, @PathVariable Long cursoId) {
		try {
			Inscripcion nuevaInscripcion = inscripcionService.inscribirse(usuarioId, cursoId);
			return ResponseEntity.ok(nuevaInscripcion);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error inesperado: " + e.getMessage()));
		}
	}

	@Operation(summary = "Listar inscripciones por usuario", description = "Obtiene todas las inscripciones activas y no activas asociadas al ID de un usuario.")
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<Inscripcion>> porUsuario(@PathVariable Long usuarioId) {
		return ResponseEntity.ok(inscripcionService.listarPorUsuario(usuarioId));
	}

	@Operation(summary = "Listar inscripciones por curso", description = "Devuelve todas las inscripciones activas asociadas al ID de un curso. Acceso solo para administradores.")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/curso/{cursoId}")
	public ResponseEntity<List<Inscripcion>> porCurso(@PathVariable Long cursoId) {
		return ResponseEntity.ok(inscripcionService.listarPorCurso(cursoId));
	}

	@Operation(summary = "Cancelar inscripción", description = "Realiza una baja lógica de una inscripción específica por su ID.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancelar(@PathVariable Long id) {
		inscripcionService.cancelarInscripcion(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Listar todas las inscripciones", description = "Obtiene todas las inscripciones, activas o no, registradas en el sistema. Acceso restringido a administradores.")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Inscripcion>> listarTodas() {
		return ResponseEntity.ok(inscripcionService.listarTodas());
	}

	@Operation(summary = "Buscar inscripción por ID", description = "Busca una inscripción específica a partir de su ID.")
	@GetMapping("/{id}")
	public ResponseEntity<Inscripcion> buscarPorId(@PathVariable Long id) {
		Inscripcion i = inscripcionService.buscarPorId(id);
		return i != null ? ResponseEntity.ok(i) : ResponseEntity.notFound().build();
	}

	@Operation(summary = "Activar inscripción", description = "Reactiva una inscripción previamente cancelada. Solo ADMIN puede hacer esto.")
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/{id}/activar")
	public ResponseEntity<Void> activar(@PathVariable Long id) {
		inscripcionService.activarInscripcion(id);
		return ResponseEntity.noContent().build();
	}

}
