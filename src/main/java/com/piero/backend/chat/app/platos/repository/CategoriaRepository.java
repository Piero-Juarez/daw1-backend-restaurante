package com.piero.backend.chat.app.platos.repository;

import com.piero.backend.chat.app.platos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Short> {
}
