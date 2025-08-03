package com.piero.backend.chat.app.menu.service;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import java.util.List;

public interface ItemMenuService {
    List<ItemMenuDTOResponse> findAllActivo();
    ItemMenuDTOResponse guardarItemMenu(ItemMenuDTORequest itemMenuDTORequest);
    ItemMenuDTOResponse buscarItemMenuPorNombre(String nombre);
    ItemMenuDTOResponse actualizarItemMenu(ItemMenuDTORequest itemMenuDTORequest);
    void EliminarItemMenu(Integer id);
}
