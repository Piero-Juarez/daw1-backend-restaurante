package com.piero.backend.chat.app.platos.controller;

import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTORequest;
import com.piero.backend.chat.app.platos.dto.categoria.CategoriaDTOResponse;
import com.piero.backend.chat.app.platos.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    @GetMapping()
    public ResponseEntity<List<CategoriaDTOResponse>> listarCategorias() {
        return ResponseEntity.ok( categoriaService.listarCategorias());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTOResponse>obtenerCategoria(@PathVariable Short id) {
        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
    }

    @PostMapping()
    public ResponseEntity<CategoriaDTOResponse> guardarCategoria(@RequestBody CategoriaDTORequest categoriaRequest) {
        CategoriaDTOResponse categoriaDTOResponse = categoriaService.guardarCategoria(categoriaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDTOResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTOResponse> actualizarCategoria(@PathVariable Short id, @RequestBody CategoriaDTORequest categoriaDTORequest) {
        CategoriaDTOResponse categoriaDtoResponse =  categoriaService.actualizarCategoria(id, categoriaDTORequest);
        return ResponseEntity.ok(categoriaDtoResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Short id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Se elimino la categoria");
    }
}
