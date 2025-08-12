package com.piero.backend.chat.app.menu.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias_items")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(unique = true)
    private String nombre;

    private String descripcion;

    @Column(name = "precio_minimo")
    private double precioMinimo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ItemMenu> itemMenus = new ArrayList<>();

    private Boolean activo;

    public void eliminarItemMenuAsociado() {
        this.activo = false;
        for (ItemMenu itemMenu : itemMenus) {
            itemMenu.setActivo(false);
        }
    }

}
