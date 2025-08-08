package com.piero.backend.chat.app.menu.service;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemMenuService {
    Page<ItemMenuDTOResponse> listarItemMenusActivo(Pageable pageable);
    ItemMenuDTOResponse guardarItemMenu(ItemMenuDTORequest itemMenuDTORequest);
    Page<ItemMenuDTOResponse> buscarItemsMenu(String nombre, String nombreCategoria, Pageable pageable);
    ItemMenuDTOResponse actualizarItemMenu(Integer id, ItemMenuDTORequest itemMenuDTORequest);
    ItemMenuDTOResponse buscarItemMenuPorId(Integer id);
    ItemMenuDTOResponse cambiarEstadoItemMenu(Integer id, String estado);
    void eliminarItemMenu(Integer id);
}
