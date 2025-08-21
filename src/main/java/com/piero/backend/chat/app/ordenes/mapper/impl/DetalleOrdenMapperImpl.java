package com.piero.backend.chat.app.ordenes.mapper.impl;

import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.mapper.DetalleOrdenMapper;
import com.piero.backend.chat.app.ordenes.model.DetalleOrden;
import org.springframework.stereotype.Component;

@Component
public class DetalleOrdenMapperImpl implements DetalleOrdenMapper {

    @Override
    public DetalleOrdenResponseDTO toDetalleResponseDto(DetalleOrden detalleOrden) {
        return DetalleOrdenResponseDTO.builder()
                .id(detalleOrden.getId())
                .nombreItemMenu(detalleOrden.getItemMenu().getNombre())
                .cantidad(detalleOrden.getCantidad())
                .precioUnitario(detalleOrden.getPrecioUnitario())
                .subtotal(detalleOrden.getSubtotal())
                .igv(detalleOrden.getMontoIgv())
                .total(detalleOrden.getTotal())
                .build();
    }

}
