package com.piero.backend.chat.app.menu.service;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.model.enums.EstadoItemMenu;

import java.util.List;

public interface ItemMenuService {
    List<ItemMenuDTOResponse> listarItemMenusActivo();
    ItemMenuDTOResponse guardarItemMenu(ItemMenuDTORequest itemMenuDTORequest);
    ItemMenuDTOResponse buscarItemMenuPorNombre(String nombre);
    ItemMenuDTOResponse actualizarItemMenu(Integer id, ItemMenuDTORequest itemMenuDTORequest);
    ItemMenuDTOResponse buscarItemMenuPorId(Integer id);
    ItemMenuDTOResponse cambiarEstadoItemMenu(Integer id, String estado);
    void eliminarItemMenu(Integer id);
}
