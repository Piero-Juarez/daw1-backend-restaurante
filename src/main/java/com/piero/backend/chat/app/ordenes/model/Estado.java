package com.piero.backend.chat.app.ordenes.model;

import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "estados_ordenes")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;
    private Boolean activo;

}
