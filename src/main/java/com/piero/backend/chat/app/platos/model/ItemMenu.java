package com.piero.backend.chat.app.platos.model;

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
@Entity(name = "platos")
public class ItemMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @ToString.Exclude
    private Categoria categoria;

    @OneToMany(mappedBy = "itemMenu")
    @ToString.Exclude
    private List<DetalleOrden> detalles;

    private Boolean activo;

}
