package com.piero.backend.chat.app.menu.repository;

import com.piero.backend.chat.app.menu.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Short> {
}
