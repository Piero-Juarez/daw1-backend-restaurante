package com.piero.backend.chat.app.menu.model;

import com.piero.backend.chat.app.menu.model.enums.EstadoItemMenu;
import com.piero.backend.chat.app.ordenes.model.DetalleOrden;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items_menu")
public class ItemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nombre;

    private String descripcion;
    private Double precio;

    @Column(name = "enlace_imagen")
    private String enlaceImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @ToString.Exclude
    private Categoria categoria;

    @OneToMany(mappedBy = "itemMenu")
    @ToString.Exclude
    private List<DetalleOrden> detalles;

    @Enumerated(EnumType.STRING)
    private EstadoItemMenu  estado = EstadoItemMenu.DISPONIBLE;

    private Boolean activo;

}
