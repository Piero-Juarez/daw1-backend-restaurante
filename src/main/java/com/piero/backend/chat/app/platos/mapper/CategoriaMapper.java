package com.piero.backend.chat.app.platos.mapper;

import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.platos.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CategoriaMapper {

    public CategoriaDTOResponse toDto(Categoria categoria){
        return new  CategoriaDTOResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
    public List<CategoriaDTOResponse> listToDto(List<Categoria> categorias) {
        return categorias.stream().map(this::toDto).toList();
    }
}
