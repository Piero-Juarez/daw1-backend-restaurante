package com.piero.backend.chat.app.ordenes.model;

import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    private String numero;
    private Short capacidad;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoMesa estado = EstadoMesa.LIBRE;

}
