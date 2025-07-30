package com.piero.backend.chat.app.platos.service;

import com.piero.backend.chat.app.platos.model.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> listarCategorias();
    Categoria guardarCategoria(Categoria categoria);
    Categoria actualizarCategoria(Categoria categoria);
    void eliminarCategoria(Categoria categoria);
}
