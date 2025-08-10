package com.piero.backend.chat.app.ordenes.repository;

import com.piero.backend.chat.app.ordenes.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Query("SELECT MAX(o.id) FROM Orden o")
    Optional<Long> findMaxId();

    List<Orden> findByFechaCreacion(LocalDate fechaCreacion);

}
