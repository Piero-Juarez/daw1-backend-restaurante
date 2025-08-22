package com.piero.backend.chat.app.ordenes.repository;

import com.piero.backend.chat.app.ordenes.model.Orden;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query("SELECT MAX(o.id) FROM Orden o")
    Optional<Long> findMaxId();

    @Query("SELECT o from Orden o WHERE o.fechaCreacion = CURRENT_DATE AND o.activo = TRUE AND (COALESCE(:estados, NULL) IS NULL OR o.estado IN :estados)")
    Page<Orden> ordenesPorFechaActivoEstado(@Param("estados") List<EstadoOrden> estados, Pageable pageable);

    Optional<Orden> findByMesa_IdAndEstadoAndActivoTrue(Short mesaId, EstadoOrden estado);

    @Modifying
    @Query("UPDATE Orden o SET o.activo = false WHERE (o.estado = 'CANCELADA' OR o.estado = 'COMPLETADA') AND o.activo = true")
    void desactivarOrdenesCanceladasCompletadas();

}
