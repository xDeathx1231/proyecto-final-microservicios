package com.ef.banner.cursos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ef.banner.cursos.dto.CursoDTO;
import com.ef.banner.cursos.dto.UsuarioDTO;
import com.ef.banner.cursos.feign.UsuarioClient;
import com.ef.banner.cursos.model.Categoria;
import com.ef.banner.cursos.model.Curso;
import com.ef.banner.cursos.rabbit.ProductorRabbitMQ;
import com.ef.banner.cursos.repository.CategoriaRepository;
import com.ef.banner.cursos.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private UsuarioClient usuarioClient;

	@Autowired
	private ProductorRabbitMQ productorRabbitMQ;

	public Curso crearCurso(CursoDTO dto) {
		// 1️⃣ Validar con usuario-service que sea PROFESOR
		UsuarioDTO usuario = usuarioClient.obtenerUsuarioPorId(dto.getProfesorId());

		if (usuario == null) {
			throw new RuntimeException("El ID de usuario no existe");
		}

		if (!"PROFESOR".equalsIgnoreCase(usuario.getRol())) {
			throw new RuntimeException("El usuario asignado no tiene rol de PROFESOR");
		}

		// 2️⃣ Validar que la categoría exista
		Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
				.orElseThrow(() -> new RuntimeException("La categoría especificada no existe"));

		// 3️⃣ Crear y guardar
		Curso curso = Curso.builder().nombre(dto.getNombre()).descripcion(dto.getDescripcion())
				.profesorId(dto.getProfesorId()).horarioPropuesto(dto.getHorarioPropuesto())
				.fechaInicio(dto.getFechaInicio()).fechaFin(dto.getFechaFin()).duracionHoras(dto.getDuracionHoras())
				.categoria(categoria).activo(dto.getActivo() != null ? dto.getActivo() : true)
				.vacantes(dto.getVacantes()).vacantesDisponibles(dto.getVacantesDisponibles())
				.creditos(dto.getCreditos()).build();

		Curso nuevoCurso = cursoRepository.save(curso);

		String mensaje = "Curso registrado: " + nuevoCurso.getNombre() + " (ID: " + nuevoCurso.getId() + ")";
		productorRabbitMQ.enviarMensaje(mensaje);

		return nuevoCurso;
	}

	public Curso actualizarCurso(Long id, Curso datos) {
		return cursoRepository.findById(id).map(curso -> {
			curso.setNombre(datos.getNombre());
			curso.setDescripcion(datos.getDescripcion());
			curso.setProfesorId(datos.getProfesorId());
			curso.setHorarioPropuesto(datos.getHorarioPropuesto());
			curso.setFechaInicio(datos.getFechaInicio());
			curso.setFechaFin(datos.getFechaFin());
			curso.setDuracionHoras(datos.getDuracionHoras());

			// Validar que la categoría exista antes de actualizar
			if (datos.getCategoria() != null && datos.getCategoria().getId() != null) {
				Categoria categoria = categoriaRepository.findById(datos.getCategoria().getId())
						.orElseThrow(() -> new RuntimeException("La categoría especificada no existe"));
				curso.setCategoria(categoria);
			}

			curso.setActivo(datos.getActivo());
			curso.setVacantes(datos.getVacantes());
			curso.setVacantesDisponibles(datos.getVacantesDisponibles());
			curso.setCreditos(datos.getCreditos());

			return cursoRepository.save(curso);
		}).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	}

	public void eliminarCurso(Long id) {
		Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
		curso.setActivo(false);
		cursoRepository.save(curso);
	}

	public List<Curso> listarActivos() {
		return cursoRepository.findByActivoTrue();
	}

	public List<Curso> listarTodos() {
		return cursoRepository.findAll();
	}

	public List<Curso> listarPorProfesor(Long idProfesor) {
		return cursoRepository.findByProfesorIdAndActivoTrue(idProfesor);
	}

	public Curso buscarPorId(Long id) {
		return cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	}

	public List<Curso> buscarCursosQueEmpiezanDespues(LocalDateTime fecha) {
		return cursoRepository.findByFechaInicioAfter(fecha);
	}

	public List<Curso> buscarCursosQueFinalizanAntes(LocalDateTime fecha) {
		return cursoRepository.findByFechaFinBefore(fecha);
	}

	private String extraerTokenActual() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getCredentials() != null) {
			return auth.getCredentials().toString();
		}
		return null;
	}
}
