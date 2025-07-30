package com.piero.backend.chat.app.platos.mapper;

import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.platos.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CategoriaMapper {

    public CategoriaDTOResponse toDtoResponse(Categoria categoria){
        return new  CategoriaDTOResponse(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
    public Categoria RequestToEntity(CategoriaDTORequest categoriaDTORequest){
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTORequest.nombre());
        categoria.setDescripcion(categoriaDTORequest.descripcion());

        return categoria;
    }
    public List<CategoriaDTOResponse> listToDto(List<Categoria> categorias) {
        return categorias.stream().map(this::toDtoResponse).toList();
    }
}
