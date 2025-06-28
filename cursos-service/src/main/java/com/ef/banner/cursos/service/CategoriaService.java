package com.ef.banner.cursos.service;

import com.ef.banner.cursos.model.Categoria;
import com.ef.banner.cursos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> listarTodas() {
		return categoriaRepository.findAll();
	}

	public Categoria obtenerPorId(Long id) {
		return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("❌ Categoría no encontrada"));
	}

	public Categoria crear(Categoria categoria) {
		if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre())) {
			throw new IllegalArgumentException("⚠️ Nombre de categoría ya existe");
		}
		return categoriaRepository.save(categoria);
	}

	public Categoria actualizar(Long id, Categoria nueva) {
		Categoria categoria = obtenerPorId(id);
		categoria.setNombre(nueva.getNombre());
		categoria.setDescripcion(nueva.getDescripcion());
		return categoriaRepository.save(categoria);
	}

	public void eliminar(Long id) {
		categoriaRepository.deleteById(id);
	}
}
