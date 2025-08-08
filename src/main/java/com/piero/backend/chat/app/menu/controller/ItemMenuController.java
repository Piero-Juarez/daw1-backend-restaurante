package com.piero.backend.chat.app.menu.controller;

import com.piero.backend.chat.app.menu.dto.itemmenu.EstadoItemMenuDtoRequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.service.ItemMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items-menu")
public class ItemMenuController {

    private final ItemMenuService itemMenuService;

    @GetMapping
    public ResponseEntity<Page<ItemMenuDTOResponse>> listarItemMenu(Pageable pageable){
        Page<ItemMenuDTOResponse> paginaDeItems = itemMenuService.listarItemMenusActivo(pageable);
        return ResponseEntity.ok(paginaDeItems);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ItemMenuDTOResponse>> buscarItemsMenu(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false, name = "categoria") String nombreCategoria,
            Pageable pageable
    ){
        Page<ItemMenuDTOResponse> paginaDeItems = itemMenuService.buscarItemsMenu(nombre, nombreCategoria, pageable);
        if (paginaDeItems == null) { return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok(paginaDeItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuDTOResponse> buscarItemMenuPorId(@PathVariable Integer id){
        ItemMenuDTOResponse result = itemMenuService.buscarItemMenuPorId(id);
        if (result == null) { return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ItemMenuDTOResponse> agregarItemMenu(@RequestBody ItemMenuDTORequest itemMenuDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuService.guardarItemMenu(itemMenuDTORequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuDTOResponse> editarItemMenu( @PathVariable Integer id, @RequestBody ItemMenuDTORequest itemMenuDTORequest){
        return ResponseEntity.ok(itemMenuService.actualizarItemMenu(id, itemMenuDTORequest));
    }

    @PutMapping("/cambiar-estado/{id}")
    public ResponseEntity<ItemMenuDTOResponse> cambiarEstadoItemMenu(@PathVariable Integer id,@RequestBody EstadoItemMenuDtoRequest estado){
        return ResponseEntity.ok(itemMenuService.cambiarEstadoItemMenu(id,estado.estado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarItemMenu(@PathVariable Integer id){
        itemMenuService.eliminarItemMenu(id);
        return ResponseEntity.ok().build();
    }

}
