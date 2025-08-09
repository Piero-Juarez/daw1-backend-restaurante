package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.mapper.ItemMenuMapper;
import com.piero.backend.chat.app.menu.model.ItemMenu;
import com.piero.backend.chat.app.menu.model.enums.EstadoItemMenu;
import com.piero.backend.chat.app.menu.repository.CategoriaRepository;
import com.piero.backend.chat.app.menu.repository.ItemMenuRepository;
import com.piero.backend.chat.app.menu.service.ItemMenuService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemMenuServiceImpl implements ItemMenuService {

    private final ItemMenuMapper itemMenuMapper;
    private final ItemMenuRepository itemMenuRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ItemMenuDTOResponse> listarItemMenusActivo(Pageable pageable) {
        return itemMenuMapper.listToDtoResponse(itemMenuRepository.findItemMenuByActivo(true, pageable));
    }

    @Override
    @Transactional
    public ItemMenuDTOResponse guardarItemMenu(ItemMenuDTORequest itemMenuDTORequest) {
        ItemMenu itemMenu = itemMenuMapper.RequestToEntity(itemMenuDTORequest);

        itemMenu.setCategoria(categoriaRepository.findById(itemMenuDTORequest.idCategoria()).orElseThrow(()-> new EntityNotFoundException("Categoria no encontrada")));
        itemMenu.setActivo(true);
        itemMenuRepository.save(itemMenu);

        return itemMenuMapper.toDtoResponse(itemMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemMenuDTOResponse> buscarItemsMenu(String nombre, String nombreCategoria, Pageable pageable) {
        Page<ItemMenu> listadoItemsMenu = itemMenuRepository.buscarItemsMenuDinamicamente(nombre, nombreCategoria, pageable);
        return itemMenuMapper.listToDtoResponse(listadoItemsMenu);
    }

    @Override
    @Transactional
    public ItemMenuDTOResponse actualizarItemMenu(Integer idMenu, ItemMenuDTORequest itemMenuDTORequest) {
        ItemMenu itemMenu = itemMenuRepository.findById(idMenu).orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrada"));

        itemMenu.setNombre(itemMenuDTORequest.nombre());
        itemMenu.setDescripcion(itemMenuDTORequest.descripcion());
        itemMenu.setPrecio(itemMenuDTORequest.precio());
        itemMenu.setEnlaceImagen(itemMenuDTORequest.enlaceImagen());

        itemMenu.setCategoria(categoriaRepository.findById(itemMenuDTORequest.idCategoria()).orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada")));
        //añadir el guardado de imagen
        itemMenuRepository.save(itemMenu);

        return itemMenuMapper.toDtoResponse(itemMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemMenuDTOResponse buscarItemMenuPorId(Integer id) {
        return itemMenuMapper.toDtoResponse(itemMenuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrada")));
    }

    @Override
    @Transactional
    public ItemMenuDTOResponse cambiarEstadoItemMenu(Integer id, String estado) {
        ItemMenu itemMenuBuscado = itemMenuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrada"));
        itemMenuBuscado.setEstado(EstadoItemMenu.valueOf(estado.toUpperCase()));
        itemMenuRepository.save(itemMenuBuscado);
        return itemMenuMapper.toDtoResponse(itemMenuBuscado);
    }

    @Override
    @Transactional
    public void eliminarItemMenu(Integer id) {
        ItemMenu itemMenuEliminar = itemMenuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrada"));
        itemMenuEliminar.setActivo(false);
        itemMenuRepository.save(itemMenuEliminar);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ItemMenu> buscarPorCategoriaYPrecio(Short categoriaId, Double precioMax) {
        return itemMenuRepository.findByCategoria_IdAndActivoTrueAndEstadoAndPrecioLessThanEqual(
                categoriaId,
                EstadoItemMenu.DISPONIBLE,
                precioMax
        );
    }

}
