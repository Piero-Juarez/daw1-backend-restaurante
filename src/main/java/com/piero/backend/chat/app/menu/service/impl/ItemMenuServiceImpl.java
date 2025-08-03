package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.mapper.ItemMenuMapper;
import com.piero.backend.chat.app.menu.model.ItemMenu;
import com.piero.backend.chat.app.menu.repository.CategoriaRepository;
import com.piero.backend.chat.app.menu.repository.ItemMenuRepository;
import com.piero.backend.chat.app.menu.service.ItemMenuService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ItemMenuServiceImpl implements ItemMenuService {
    private final ItemMenuMapper itemMenuMapper;
    private final ItemMenuRepository itemMenuRepository;
    private final CategoriaRepository categoriaRepository;
    @Override
    public List<ItemMenuDTOResponse> listarItemMenusActivo() {
        return itemMenuMapper.listToDtoResponse(itemMenuRepository.findAll());
    }

    @Override
    public ItemMenuDTOResponse guardarItemMenu(ItemMenuDTORequest itemMenuDTORequest) {
        ItemMenu itemMenu = itemMenuMapper.RequestToEntity(itemMenuDTORequest);

        itemMenu.setCategoria(categoriaRepository.findById(itemMenuDTORequest.idCategoria())
                .orElseThrow(()-> new EntityNotFoundException("Categoria no encontrada")));
        //añadir el guardado de imagen
        itemMenuRepository.save(itemMenu);

        return itemMenuMapper.toDtoResponse(itemMenu);
    }

    @Override
    public ItemMenuDTOResponse buscarItemMenuPorNombre(String nombre) {
        return itemMenuMapper.toDtoResponse(itemMenuRepository.findByNombre(nombre));
    }

    @Override
    public ItemMenuDTOResponse actualizarItemMenu(Integer idMenu, ItemMenuDTORequest itemMenuDTORequest) {
        ItemMenu itemMenu = itemMenuRepository.findById(idMenu)
                .orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrada"));

        itemMenu.setNombre(itemMenuDTORequest.nombre());
        itemMenu.setDescripcion(itemMenuDTORequest.descripcion());
        itemMenu.setPrecio(itemMenuDTORequest.precio());
        itemMenu.setImagen(itemMenuDTORequest.nombreImagen());

        itemMenu.setCategoria(categoriaRepository.findById(itemMenuDTORequest.idCategoria())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada")));
        //añadir el guardado de imagen
        itemMenuRepository.save(itemMenu);

        return itemMenuMapper.toDtoResponse(itemMenu);
    }


    @Override
    public void eliminarItemMenu(Integer id) {
        itemMenuRepository.deleteById(id);
    }
}
