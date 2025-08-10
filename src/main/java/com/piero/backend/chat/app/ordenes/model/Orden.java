package com.piero.backend.chat.app.ordenes.model;

import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 14, unique = true)
    private String codigoOrden;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<DetalleOrden> detalles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id", nullable = false)
    @ToString.Exclude
    private Mesa mesa;

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "hora_creacion")
    private LocalTime horaCreacion;

    @Column(name = "monto_subtotal")
    private Double montoSubtotal; // Suma de todos los subtotales de DetalleOrden

    @Column(name = "monto_total")
    private Double montoTotal; // Total final del pedido.

    private Boolean activo;

}
