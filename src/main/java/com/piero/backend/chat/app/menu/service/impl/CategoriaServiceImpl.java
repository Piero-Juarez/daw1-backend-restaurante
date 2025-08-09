package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.menu.mapper.CategoriaMapper;
import com.piero.backend.chat.app.menu.model.Categoria;
import com.piero.backend.chat.app.menu.repository.CategoriaRepository;
import com.piero.backend.chat.app.menu.service.CategoriaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    @Override
    public List<CategoriaDTOResponse> listarCategorias() {
        return categoriaMapper.listToDto(categoriaRepository.findAllByActivo(true));
    }

    @Override
    public CategoriaDTOResponse guardarCategoria(CategoriaDTORequest categoriaRequest) {
        Categoria categoria = categoriaMapper.RequestToEntity(categoriaRequest);
        categoria.setActivo(true);
        return categoriaMapper.toDtoResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDTOResponse actualizarCategoria(Short id, CategoriaDTORequest categoriaRequest) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        categoria.setNombre(categoriaRequest.nombre());
        categoria.setDescripcion(categoriaRequest.descripcion());
        categoriaRepository.save(categoria);
        return categoriaMapper.toDtoResponse(categoria);
    }

    @Override
    public CategoriaDTOResponse obtenerCategoriaPorId(Short id) {
        return categoriaMapper.toDtoResponse(categoriaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con id: " + id))) ;
    }

    @Override
    public void eliminarCategoria(Short idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));
        categoria.eliminarItemMenuAsociado();
        categoriaRepository.save(categoria);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> obtenerCategoriasActivas() {
        return categoriaRepository.findByActivoTrue();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> buscarPorId(Short id) {
        return categoriaRepository.findById(id);
    }
}
