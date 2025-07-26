package com.piero.backend.chat.app.platos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.zip.ZipEntry;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "categorias_platos")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Plato> platos;

    private Boolean activo;

}
