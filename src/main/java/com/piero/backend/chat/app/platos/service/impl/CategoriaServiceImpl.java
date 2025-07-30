package com.piero.backend.chat.app.platos.service.impl;

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
    @Override
    public List<Categoria> listarCategorias() {
        return null;
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return null;
    }

    @Override
    public Categoria actualizarCategoria(Categoria categoria) {
        return null;
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {

    }
}
