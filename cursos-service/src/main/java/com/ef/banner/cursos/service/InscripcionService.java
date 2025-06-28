package com.ef.banner.cursos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ef.banner.cursos.feign.UsuarioClient;
import com.ef.banner.cursos.model.Curso;
import com.ef.banner.cursos.model.Inscripcion;
import com.ef.banner.cursos.model.Pago;
import com.ef.banner.cursos.rabbit.ProductorRabbitMQ;
import com.ef.banner.cursos.repository.CursoRepository;
import com.ef.banner.cursos.repository.InscripcionRepository;
import com.ef.banner.cursos.repository.PagoRepository;

@Service
public class InscripcionService {

	@Autowired
	private InscripcionRepository inscripcionRepository;

	@Autowired
	private ProductorRabbitMQ productorRabbitMQ;

	@Autowired
	private PagoRepository pagoRepository;

	@Autowired
	private UsuarioClient usuarioClient;

	@Autowired
	private CursoRepository cursoRepository;

	// ✅ InscripcionService.java

	private static final double PRECIO_POR_CREDITO = 20.0;

	public Inscripcion inscribirse(Long usuarioId, Long cursoId) {

		// ✔️ Verificar si ya existe inscripción activa
		if (inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivoTrue(usuarioId, cursoId)) {
			throw new IllegalArgumentException("⚠️ Ya estás inscrito en este curso.");
		}

		// ✔️ Buscar curso
		Curso curso = cursoRepository.findById(cursoId)
				.orElseThrow(() -> new IllegalArgumentException("❌ Curso no encontrado."));

		if (!curso.getActivo()) {
			throw new IllegalArgumentException("⚠️ El curso no está activo actualmente.");
		}

		if (curso.getVacantesDisponibles() == null || curso.getVacantesDisponibles() <= 0) {
			throw new IllegalArgumentException("🚫 No hay vacantes disponibles en este curso.");
		}

		if (curso.getCreditos() == null || curso.getCreditos() < 1 || curso.getCreditos() > 5) {
			throw new IllegalArgumentException(
					"❌ Créditos inválidos para este curso. Debe tener entre 1 y 5 créditos.");
		}

		// ✔️ Calcular costo
		double costo = curso.getCreditos() * PRECIO_POR_CREDITO;

		// ✔️ Descontar saldo en usuario-service
		try {
			usuarioClient.descontarSaldo(usuarioId, costo);
		} catch (Exception ex) {
			throw new IllegalStateException("⚠️ No se pudo procesar el pago. Verifica tu saldo disponible.");
		}

		// ✔️ Actualizar vacantes del curso
		curso.setVacantesDisponibles(curso.getVacantesDisponibles() - 1);
		cursoRepository.save(curso);

		// ✔️ Registrar inscripción
		Inscripcion inscripcion = Inscripcion.builder().usuarioId(usuarioId).cursoId(cursoId)
				.fechaInscripcion(LocalDateTime.now()).activo(true).build();

		Inscripcion nuevaInscripcion = inscripcionRepository.save(inscripcion);

		// ✔️ Registrar pago
		Pago pago = Pago.builder().usuarioId(usuarioId).cursoId(cursoId).monto(costo).fechaPago(LocalDateTime.now())
				.descripcion("Inscripción al curso: " + curso.getNombre()).build();

		pagoRepository.save(pago);

		// ✔️ Enviar mensaje a RabbitMQ
		productorRabbitMQ
				.enviarMensaje("🧾 Usuario " + usuarioId + " pagó S/" + costo + " por inscripción en curso " + cursoId);

		return nuevaInscripcion;
	}

	public List<Inscripcion> listarPorUsuario(Long usuarioId) {
		return inscripcionRepository.findByUsuarioId(usuarioId);
	}

	public List<Inscripcion> listarPorCurso(Long cursoId) {
		return inscripcionRepository.findByCursoIdAndActivoTrue(cursoId);
	}

	public void cancelarInscripcion(Long id) {
		Inscripcion insc = inscripcionRepository.findById(id).orElse(null);
		if (insc != null) {
			insc.setActivo(false);
			inscripcionRepository.save(insc);
		}
	}

	public List<Inscripcion> listarTodas() {
		return inscripcionRepository.findAll();
	}

	public Inscripcion buscarPorId(Long id) {
		return inscripcionRepository.findById(id).orElse(null);
	}

	public void activarInscripcion(Long id) {
		Inscripcion inscripcion = inscripcionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

		inscripcion.setActivo(true);
		inscripcionRepository.save(inscripcion);
	}

}
