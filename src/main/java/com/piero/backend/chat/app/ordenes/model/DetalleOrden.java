package com.piero.backend.chat.app.ordenes.model;

import com.piero.backend.chat.app.menu.model.ItemMenu;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_ordenes")
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
    private ItemMenu itemMenu;

    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    private Double igv = 0.18;

    private Double subtotal; // cantidad * precioUnitario sin IGV ni descuentos.
    private Double total; // Monto total a pagar por este detalle (ya con IGV).
    private Boolean activo;

}
