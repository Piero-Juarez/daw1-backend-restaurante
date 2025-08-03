package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.service.ItemMenuService;

import java.util.List;

public class ItemMenuServiceImpl implements ItemMenuService {
    @Override
    public List<ItemMenuDTOResponse> findAllActivo() {
        return List.of();
    }

    @Override
    public ItemMenuDTOResponse guardarItemMenu(ItemMenuDTORequest itemMenuDTORequest) {
        return null;
    }

    @Override
    public ItemMenuDTOResponse buscarItemMenuPorNombre(String nombre) {
        return null;
    }

    @Override
    public ItemMenuDTOResponse actualizarItemMenu(ItemMenuDTORequest itemMenuDTORequest) {
        return null;
    }

    @Override
    public void EliminarItemMenu(Integer id) {

    }
}
