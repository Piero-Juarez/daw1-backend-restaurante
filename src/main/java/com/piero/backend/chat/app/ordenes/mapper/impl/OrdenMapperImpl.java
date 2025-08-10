package com.piero.backend.chat.app.ordenes.mapper.impl;

import com.piero.backend.chat.app.config.AppUtils;
import com.piero.backend.chat.app.menu.model.ItemMenu;
import com.piero.backend.chat.app.menu.repository.ItemMenuRepository;
import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.mapper.DetalleOrdenMapper;
import com.piero.backend.chat.app.ordenes.mapper.OrdenMapper;
import com.piero.backend.chat.app.ordenes.model.DetalleOrden;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.Orden;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import com.piero.backend.chat.app.ordenes.repository.OrdenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class OrdenMapperImpl implements OrdenMapper {

    private final OrdenRepository ordenRepository;
    private final ItemMenuRepository itemMenuRepository;
    private final DetalleOrdenMapper detalleOrdenMapper;

    @Override
    public Orden toEntity(OrdenRequestDTO ordenRequestDTO, Mesa mesa) {

        Orden orden = new Orden();
        orden.setMesa(mesa);
        orden.setActivo(true);
        orden.setEstado(EstadoOrden.PENDIENTE);
        orden.setFechaCreacion(LocalDate.now());
        orden.setHoraCreacion(LocalTime.now());
        orden.setCodigoOrden(generarCodigoOrden());

        AtomicReference<Double> montoSubtotalOrden = new AtomicReference<>(0.0);
        AtomicReference<Double> montoTotalOrden = new AtomicReference<>(0.0);

        List<DetalleOrden> detalles = ordenRequestDTO.detalles().stream().map(detalleDto -> {
            ItemMenu item = itemMenuRepository.findById(detalleDto.itemMenuId()).orElseThrow(() -> new EntityNotFoundException("ItemMenu no encontrado con ID: " + detalleDto.itemMenuId()));
            DetalleOrden detalleOrden = new DetalleOrden();
            detalleOrden.setOrden(orden);
            detalleOrden.setItemMenu(item);
            detalleOrden.setCantidad(detalleDto.cantidad());
            detalleOrden.setPrecioUnitario(item.getPrecio());
            detalleOrden.setActivo(true);

            double subTotalDetalle = detalleOrden.getCantidad() * detalleOrden.getPrecioUnitario();
            double totalDetalle = subTotalDetalle * (1 + detalleOrden.getIgv());
            detalleOrden.setSubtotal(subTotalDetalle);
            detalleOrden.setTotal(totalDetalle);

            montoSubtotalOrden.updateAndGet(subtotal -> subtotal + subTotalDetalle);
            montoTotalOrden.updateAndGet(total -> total + totalDetalle);
            return detalleOrden;
        }).toList();

        orden.setDetalles(detalles);
        orden.setMontoSubtotal(montoSubtotalOrden.get());
        orden.setMontoTotal(montoTotalOrden.get());
        return orden;
    }

    @Override
    public OrdenResponseDTO toResponseDto(Orden orden) {
        List<DetalleOrdenResponseDTO> detallesDto = orden.getDetalles().stream().map(detalleOrdenMapper::toDetalleResponseDto).toList();
        return OrdenResponseDTO.builder()
                .id(orden.getId())
                .codigoOrden(orden.getCodigoOrden())
                .mesaId(orden.getMesa().getId())
                .numeroMesa(orden.getMesa().getNumero())
                .estadoOrden(AppUtils.capitalizarTexto(orden.getEstado().name()))
                .fechaCreacion(orden.getFechaCreacion())
                .horaCreacion(orden.getHoraCreacion())
                .montoSubTotal(orden.getMontoSubtotal())
                .montoTotal(orden.getMontoTotal())
                .detalles(detallesDto)
                .build();
    }

    private String generarCodigoOrden() {
        Long ultimoId = ordenRepository.findMaxId().orElse(0L);
        Long siguienteId = ultimoId + 1;
        return String.format("ORDEN-%08d", siguienteId);
    }

}
