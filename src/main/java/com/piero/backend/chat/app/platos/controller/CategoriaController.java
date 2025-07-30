package com.piero.backend.chat.app.platos.controller;

import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.platos.mapper.CategoriaMapper;
import com.piero.backend.chat.app.platos.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    @GetMapping()
    public ResponseEntity<List<CategoriaDTOResponse>> listarCategorias() {
        List<CategoriaDTOResponse> listarCategorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(listarCategorias);
    }
}
