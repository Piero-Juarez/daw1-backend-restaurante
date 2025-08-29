package com.piero.backend.chat.app.ordenes.repository;

import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTORequest;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Short> {

    List<Mesa> findAllByEstado(EstadoMesa estado);

    @Query("SELECT o.mesa FROM Orden o WHERE o.estado = :estado AND o.activo = true")
    List<Mesa> encontrarMesasPorEstadoOrden(@Param("estado") EstadoOrden estado);

    Boolean existsMesaByNumero(String numero);


}
