package com.piero.backend.chat.app.ordenes.dto.orden;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenResponseDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record OrdenResponseDTO(
        Long id,
        @JsonProperty("codigo_orden") String codigoOrden,
        @JsonProperty("mesa_id") Short mesaId,
        @JsonProperty("numero_mesa") String numeroMesa,
        @JsonProperty("estado_orden") String estadoOrden,
        @JsonProperty("fecha_creacion") LocalDate fechaCreacion,
        @JsonProperty("hora_creacion") LocalTime horaCreacion,
        @JsonProperty("monto_sub_total") Double montoSubTotal,
        @JsonProperty("monto_total") Double montoTotal,
        List<DetalleOrdenResponseDTO> detalles
) { }
