package com.piero.backend.chat.app.reports.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetalleInfo {
    private String nombreItem;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
}
