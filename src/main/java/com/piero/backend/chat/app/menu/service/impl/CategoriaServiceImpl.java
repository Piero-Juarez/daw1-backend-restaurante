package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.exception.ErrorResponse;
import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.menu.mapper.CategoriaMapper;
import com.piero.backend.chat.app.menu.model.Categoria;
import com.piero.backend.chat.app.menu.repository.CategoriaRepository;
import com.piero.backend.chat.app.menu.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if (categoriaRepository.existsCategoriaByNombre(categoriaRequest.nombre())) {
            throw new ErrorResponse("La categoría "+categoriaRequest.nombre() + " ya existe. Debe ingresar uno nuevo.", HttpStatus.CONFLICT);
        }
        if(categoriaRequest.precioMinimo() <= 0){
            throw new ErrorResponse("El precio minimo debe ser mayor que S/0", HttpStatus.BAD_REQUEST);
        }
        Categoria categoria = categoriaMapper.RequestToEntity(categoriaRequest);
        categoria.setActivo(true);
        return categoriaMapper.toDtoResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDTOResponse actualizarCategoria(Short id, CategoriaDTORequest categoriaRequest) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new ErrorResponse("Categoria no encontrada con id: " + id, HttpStatus.NOT_FOUND));
        categoria.setNombre(categoriaRequest.nombre());
        categoria.setDescripcion(categoriaRequest.descripcion());
        categoria.setPrecioMinimo(categoriaRequest.precioMinimo());
        categoriaRepository.save(categoria);
        return categoriaMapper.toDtoResponse(categoria);
    }

    @Override
    public CategoriaDTOResponse obtenerCategoriaPorId(Short id) {
        return categoriaMapper.toDtoResponse(categoriaRepository.findById(id).orElseThrow(() -> new ErrorResponse("Categoria no encontrada con id: " + id, HttpStatus.NOT_FOUND))) ;
    }

    @Override
    public void eliminarCategoria(Short idCategoria) {
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(() -> new ErrorResponse("Categoria no encontrada con id: " + idCategoria, HttpStatus.NOT_FOUND));
        categoria.eliminarItemMenuAsociado();
        categoriaRepository.save(categoria);
    }
}
