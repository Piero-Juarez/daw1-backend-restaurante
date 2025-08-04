package com.piero.backend.chat.app.menu.mapper;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.model.ItemMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemMenuMapper {
    private final CategoriaMapper categoriaMapper;


    public ItemMenuDTOResponse toDtoResponse (ItemMenu itemMenu){
        return ItemMenuDTOResponse
                .builder()
                .idItemMenu(itemMenu.getId())
                .nombreItemMenu(itemMenu.getNombre())
                .descripcionItemMenu(itemMenu.getDescripcion())
                .precioItemMenu(itemMenu.getPrecio())
                .nombreImagenItemMenu(itemMenu.getNombreImagen())
                .categoriaDTOResponse(categoriaMapper.toDtoResponse(itemMenu.getCategoria()))
                .build();
    }
    public ItemMenu RequestToEntity(ItemMenuDTORequest itemMenuDTORequest){
        return ItemMenu.builder()
                .nombre(itemMenuDTORequest.nombre())
                .descripcion(itemMenuDTORequest.descripcion())
                .precio(itemMenuDTORequest.precio())
                //La imagen sera guardada desed el service
                .nombreImagen(itemMenuDTORequest.nombreImagen())
                //La categoria sera insertada desde el service
                .build();
    }
    public List<ItemMenuDTOResponse> listToDtoResponse (List<ItemMenu> itemMenu){
        return itemMenu.stream().map(this::toDtoResponse).toList();
    }

}
