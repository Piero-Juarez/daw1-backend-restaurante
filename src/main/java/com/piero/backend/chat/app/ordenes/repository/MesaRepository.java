package com.piero.backend.chat.app.ordenes.repository;

import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Short> {
    List<Mesa> findAllByEstado(EstadoMesa estado);
}
