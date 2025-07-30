package com.piero.backend.chat.app.platos.service.impl;

import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.platos.mapper.CategoriaMapper;
import com.piero.backend.chat.app.platos.model.Categoria;
import com.piero.backend.chat.app.platos.repository.CategoriaRepository;
import com.piero.backend.chat.app.platos.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public CategoriaDTORequest actualizarCategoria(CategoriaDTORequest categoriaRequest) {
        return null;
    }

    @Override
    public void eliminarCategoria(Integer idCategoria) {

    }
}
