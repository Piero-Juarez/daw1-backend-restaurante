package com.piero.backend.chat.app.menu.service;

import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.menu.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<CategoriaDTOResponse> listarCategorias();
    CategoriaDTOResponse guardarCategoria(CategoriaDTORequest categoriaRequest);
    CategoriaDTOResponse actualizarCategoria(Short id, CategoriaDTORequest categoriaRequest);
    CategoriaDTOResponse obtenerCategoriaPorId(Short id);
    void eliminarCategoria(Short idCategoria);
    List<Categoria> obtenerCategoriasActivas();
    Optional<Categoria> buscarPorId(Short id);
}
