package com.piero.backend.chat.app.menu.service.impl;

import com.piero.backend.chat.app.exception.ErrorResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        Boolean isExistNombre =itemMenuRepository.existsByNombreAndActivoTrue(itemMenuDTORequest.nombre());
        if(isExistNombre){
            throw new ErrorResponse("EL nombre de :" + itemMenuDTORequest.nombre() + " ya existe", HttpStatus.CONFLICT); //ToDo: Arreglar esta exception que no mande un JWT
        }
        Categoria categoriaElegida = categoriaRepository.findById(itemMenuDTORequest.idCategoria()).orElseThrow(()-> new ErrorResponse("Categoria no encontrada con id: " + itemMenuDTORequest.idCategoria(), HttpStatus.NOT_FOUND));

        controlErroresPrecio(itemMenuDTORequest, categoriaElegida);

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
        ItemMenu itemMenu = itemMenuRepository.findById(idMenu).orElseThrow(() -> new ErrorResponse("ItemMenu no encontrada con id: " + idMenu, HttpStatus.NOT_FOUND));
        Categoria categoriaElegida = categoriaRepository.findById(itemMenuDTORequest.idCategoria()).orElseThrow(()-> new ErrorResponse("Categoria no encontrada con id: " + itemMenuDTORequest.idCategoria(), HttpStatus.NOT_FOUND));

        Boolean isExistNombre =itemMenuRepository.existsByNombreAndActivoTrue(itemMenuDTORequest.nombre());
        if(isExistNombre){
            throw new ErrorResponse("EL nombre de :" + itemMenuDTORequest.nombre() + " ya existe", HttpStatus.CONFLICT); //ToDo: Arreglar esta exception que no mande un JWT
        }
        controlErroresPrecio(itemMenuDTORequest, categoriaElegida);

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
        return itemMenuMapper.toDtoResponse(itemMenuRepository.findById(id).orElseThrow(() -> new ErrorResponse("ItemMenu no encontrada con id: " + id, HttpStatus.NOT_FOUND)));
    }

    @Override
    @Transactional
    public ItemMenuDTOResponse cambiarEstadoItemMenu(Integer id, EstadoItemMenuDtoRequest dto) {
        ItemMenu itemMenuBuscado = itemMenuRepository.findById(id).orElseThrow(() -> new ErrorResponse("ItemMenu no encontrada con id: " + id, HttpStatus.NOT_FOUND));
        itemMenuBuscado.setEstado(EstadoItemMenu.valueOf(dto.estado().toUpperCase()));
        itemMenuRepository.save(itemMenuBuscado);
        return itemMenuMapper.toDtoResponse(itemMenuBuscado);
    }

    @Override
    @Transactional
    public void eliminarItemMenu(Integer id) {
        ItemMenu itemMenuEliminar = itemMenuRepository.findById(id).orElseThrow(() -> new ErrorResponse("ItemMenu no encontrada con id: " + id, HttpStatus.NOT_FOUND));
        if (itemMenuRepository.existsInActiveOrder(id)) {
            throw new ErrorResponse("No se puede eliminar este ítem del menú porque está en una orden activa o en proceso de preparación.", HttpStatus.BAD_REQUEST);
        }
        itemMenuEliminar.setActivo(false);
        itemMenuRepository.save(itemMenuEliminar);
    }

    public void controlErroresPrecio(ItemMenuDTORequest itemMenuDTORequest, Categoria categoriaElegida) {
        if(itemMenuDTORequest.precio()<=0){
            throw new ErrorResponse("El precio debe ser mayor a S/ 0", HttpStatus.BAD_REQUEST);
        }
        if (itemMenuDTORequest.precio()<categoriaElegida.getPrecioMinimo()){
            throw new ErrorResponse("El precio debe ser mayor a S/ " +  categoriaElegida.getPrecioMinimo(), HttpStatus.BAD_REQUEST);
        }
    }
}
