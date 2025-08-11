package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.exception.BusinessError;
import com.piero.backend.chat.app.menu.dto.itemmenu.EstadoItemMenuDtoRequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.mapper.ItemMenuMapper;
import com.piero.backend.chat.app.menu.model.Categoria;
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
        Categoria categoriaElegida = categoriaRepository.findById(itemMenuDTORequest.idCategoria())
                .orElseThrow(()-> new BusinessError("Categoria no encontrada con id: " + itemMenuDTORequest.idCategoria()));

        controlErrores(itemMenuDTORequest, categoriaElegida);

        ItemMenu itemMenu = itemMenuMapper.RequestToEntity(itemMenuDTORequest);
        itemMenu.setCategoria(categoriaElegida);
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
        ItemMenu itemMenu = itemMenuRepository.findById(idMenu)
                .orElseThrow(() -> new BusinessError("ItemMenu no encontrada con id: "+idMenu));
        Categoria categoriaElegida = categoriaRepository.findById(itemMenuDTORequest.idCategoria())
                .orElseThrow(()-> new BusinessError("Categoria no encontrada con id: " + itemMenuDTORequest.idCategoria()));

        controlErrores(itemMenuDTORequest, categoriaElegida);

        itemMenu.setNombre(itemMenuDTORequest.nombre());
        itemMenu.setDescripcion(itemMenuDTORequest.descripcion());
        itemMenu.setPrecio(itemMenuDTORequest.precio());
        itemMenu.setEnlaceImagen(itemMenuDTORequest.enlaceImagen());
        itemMenu.setCategoria(categoriaElegida);
        //añadir el guardado de imagen
        itemMenuRepository.save(itemMenu);

        return itemMenuMapper.toDtoResponse(itemMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemMenuDTOResponse buscarItemMenuPorId(Integer id) {
        return itemMenuMapper.toDtoResponse(itemMenuRepository.findById(id)
                .orElseThrow(() -> new BusinessError("ItemMenu no encontrada con id: "+id)));
    }

    @Override
    @Transactional
    public ItemMenuDTOResponse cambiarEstadoItemMenu(Integer id, EstadoItemMenuDtoRequest dto) {
        ItemMenu itemMenuBuscado = itemMenuRepository.findById(id)
                .orElseThrow(() -> new BusinessError("ItemMenu no encontrada con id: "+id));
        itemMenuBuscado.setEstado(EstadoItemMenu.valueOf(dto.estado().toUpperCase()));
        itemMenuRepository.save(itemMenuBuscado);
        return itemMenuMapper.toDtoResponse(itemMenuBuscado);
    }

    @Override
    @Transactional
    public void eliminarItemMenu(Integer id) {
        ItemMenu itemMenuEliminar = itemMenuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrada con id: "+id));
        itemMenuEliminar.setActivo(false);
        itemMenuRepository.save(itemMenuEliminar);
    }

    public void controlErrores(ItemMenuDTORequest itemMenuDTORequest, Categoria categoriaElegida) {
        if(itemMenuDTORequest.precio()<=0){
            throw new BusinessError("El precio debe ser mayor a S/ 0");
        }
        if (itemMenuDTORequest.precio()<categoriaElegida.getPrecioMinimo()){
            throw new BusinessError("El precio debe ser mayor a S/ " +  categoriaElegida.getPrecioMinimo());
        }
        if(itemMenuRepository.existsByNombre(itemMenuDTORequest.nombre())){
            throw new BusinessError("EL nombre de :" + itemMenuDTORequest.nombre() + " ya existe");
        }
    }
}
