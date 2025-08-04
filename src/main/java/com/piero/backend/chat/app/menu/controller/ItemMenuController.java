package com.piero.backend.chat.app.menu.controller;

import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTORequest;
import com.piero.backend.chat.app.menu.dto.itemmenu.ItemMenuDTOResponse;
import com.piero.backend.chat.app.menu.repository.ItemMenuRepository;
import com.piero.backend.chat.app.menu.service.ItemMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item-menu")
public class ItemMenuController {
    private final ItemMenuService itemMenuService;
    @GetMapping
    public ResponseEntity<List<ItemMenuDTOResponse>>  listarItemMenu(){
        return ResponseEntity.ok(itemMenuService.listarItemMenusActivo());
    }
    @PostMapping
    public ResponseEntity<ItemMenuDTOResponse>  agregarItemMenu(@RequestBody ItemMenuDTORequest itemMenuDTORequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuService.guardarItemMenu(itemMenuDTORequest));
    }
    @GetMapping("buscar/{nombre}")
    public ResponseEntity<ItemMenuDTOResponse>  buscarItemMenuPorNombre(@PathVariable String nombre){
        ItemMenuDTOResponse result = itemMenuService.buscarItemMenuPorNombre(nombre);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuDTOResponse>  buscarItemMenuPorId(@PathVariable Integer id){
        ItemMenuDTOResponse result = itemMenuService.buscarItemMenuPorId(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuDTOResponse>  editarItemMenu( @PathVariable Integer id, @RequestBody ItemMenuDTORequest itemMenuDTORequest){
        return ResponseEntity.ok(itemMenuService.actualizarItemMenu(id, itemMenuDTORequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarItemMenu(@PathVariable Integer id){
        itemMenuService.eliminarItemMenu(id);
        return ResponseEntity.ok().build();
    }
}
