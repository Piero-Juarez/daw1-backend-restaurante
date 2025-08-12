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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items-menu")
public class ItemMenuController {

    private final ItemMenuService itemMenuService;
    private final SimpMessagingTemplate messagingTemplate;

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
        ItemMenuDTOResponse itemMenuGuardado = itemMenuService.guardarItemMenu(itemMenuDTORequest);
        messagingTemplate.convertAndSend("/topic/items-menu/guardado", itemMenuGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuDTOResponse> editarItemMenu( @PathVariable Integer id, @RequestBody ItemMenuDTORequest itemMenuDTORequest){
        ItemMenuDTOResponse itemMenuEditado = itemMenuService.actualizarItemMenu(id, itemMenuDTORequest);
        messagingTemplate.convertAndSend("/topic/items-menu/editado", itemMenuEditado);
        return ResponseEntity.ok(itemMenuEditado);
    }

    @PatchMapping("/cambiar-estado/{id}")
    public ResponseEntity<ItemMenuDTOResponse> cambiarEstadoItemMenu(@PathVariable Integer id,@RequestBody EstadoItemMenuDtoRequest estado){
        ItemMenuDTOResponse itemMenuActualizado = itemMenuService.cambiarEstadoItemMenu(id,estado);
        messagingTemplate.convertAndSend("/topic/items-menu/cambio-estado", itemMenuActualizado);
        return ResponseEntity.ok(itemMenuActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarItemMenu(@PathVariable Integer id){
        itemMenuService.eliminarItemMenu(id);
        messagingTemplate.convertAndSend("/topic/items-menu/eliminado", id);
        return ResponseEntity.ok().build();
    }

}
