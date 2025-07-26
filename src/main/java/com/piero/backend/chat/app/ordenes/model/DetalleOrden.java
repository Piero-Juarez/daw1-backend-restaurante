package com.piero.backend.chat.app.ordenes.model;

import com.piero.backend.chat.app.platos.model.Plato;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "detalle_ordenes")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orden_id", nullable = false)
    @ToString.Exclude
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato_id", nullable = false)
    @ToString.Exclude
    private Plato plato;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Value("${appication.ordenes.detalle.igv}")
    private Double igv;

    private Double subtotal; // cantidad * precioUnitario sin IGV ni descuentos.
    private Double total; // Monto total a pagar por este detalle (ya con IGV).
    private Boolean activo;

}
