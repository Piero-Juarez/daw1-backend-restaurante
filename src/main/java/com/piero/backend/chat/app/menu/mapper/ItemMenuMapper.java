package com.piero.backend.chat.app.menu.mapper;

import com.piero.backend.chat.app.config.AppUtils;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.model.ItemMenu;
import com.piero.backend.chat.app.menu.model.enums.EstadoItemMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMenuMapper {

    private final CategoriaMapper categoriaMapper;

    public ItemMenuDTOResponse toDtoResponse (ItemMenu itemMenu){
        return ItemMenuDTOResponse
                .builder()
                .id(itemMenu.getId())
                .nombre(itemMenu.getNombre())
                .descripcion(itemMenu.getDescripcion())
                .precio(itemMenu.getPrecio())
                .enlaceImagen(itemMenu.getEnlaceImagen())
                .categoria(categoriaMapper.toDtoResponse(itemMenu.getCategoria()))
                .estado(AppUtils.capitalizarTexto(itemMenu.getEstado().name()))
                .build();
    }

    public ItemMenu RequestToEntity(ItemMenuDTORequest itemMenuDTORequest){
        return ItemMenu.builder()
                .nombre(itemMenuDTORequest.nombre())
                .descripcion(itemMenuDTORequest.descripcion())
                .precio(itemMenuDTORequest.precio())
                //La imagen sera guardada desed el service
                .enlaceImagen(itemMenuDTORequest.enlaceImagen())
                .estado(EstadoItemMenu.valueOf(itemMenuDTORequest.estado().toUpperCase()))
                //La categoria sera insertada desde el service
                .build();
    }

    public Page<ItemMenuDTOResponse> listToDtoResponse (Page<ItemMenu> itemMenuPage){
        return itemMenuPage.map(this::toDtoResponse);
    }

}
