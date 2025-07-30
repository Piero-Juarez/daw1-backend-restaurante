package com.piero.backend.chat.app.platos.repository;

import com.piero.backend.chat.app.platos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Short> {
    List<Categoria> findAllByActivo(Boolean activo);



}
