package com.piero.backend.chat.app.menu.repository;

import com.piero.backend.chat.app.menu.model.ItemMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMenuRepository extends JpaRepository<ItemMenu, Integer> {
    Page<ItemMenu> findItemMenuByActivo(boolean activo, Pageable pageable);

    @Query("""
        SELECT i FROM ItemMenu i WHERE
            (:nombre IS NULL OR lower(i.nombre) LIKE lower(concat('%', :nombre, '%'))) AND
            (:nombreCategoria IS NULL OR i.categoria.nombre = :nombreCategoria)
    """)
    Page<ItemMenu> buscarItemsMenuDinamicamente(
            @Param("nombre") String nombre,
            @Param("nombreCategoria") String nombreCategoria,
            Pageable pageable
    );

    //List<ItemMenu> findByCategoria_Id(Short categoriaId);

    boolean existsByNombreAndActivoTrue(String nombre);

    @Query("SELECT COUNT(d) > 0 FROM DetalleOrden d JOIN d.orden o WHERE d.itemMenu.id = :itemId AND o.activo = true AND (o.estado = 'PENDIENTE' OR o.estado = 'PREPARANDO')")
    boolean existsInActiveOrder(@Param("itemId") Integer itemId);
}
