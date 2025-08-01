package com.piero.backend.chat.app.menu.service;

import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDTOResponse> listarCategorias();
    CategoriaDTOResponse guardarCategoria(CategoriaDTORequest categoriaRequest);
    CategoriaDTOResponse actualizarCategoria(Short id, CategoriaDTORequest categoriaRequest);
    CategoriaDTOResponse obtenerCategoriaPorId(Short id);
    void eliminarCategoria(Short idCategoria);
}
