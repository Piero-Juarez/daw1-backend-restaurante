package com.piero.backend.chat.app.menu.repository;

import com.piero.backend.chat.app.menu.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Short> {
    List<Categoria> findAllByActivo(Boolean activo);

    Short id(Short id);
}
