package com.piero.backend.chat.app.ordenes.mapper.impl;

import com.piero.backend.chat.app.config.AppUtils;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTORequest;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTOResponse;
import com.piero.backend.chat.app.ordenes.mapper.MesaMapper;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MesaMapperImpl implements MesaMapper {

    @Override
    public MesaDTOResponse toDto(Mesa mesa) {
        if (mesa == null) { return null; }
        return MesaDTOResponse
                .builder()
                .id(mesa.getId())
                .numero(devolverFormato(mesa.getNumero()))
                .capacidad(mesa.getCapacidad())
                .estado(AppUtils.capitalizarTexto(mesa.getEstado().name()))
                .build();
    }

    @Override
    public List<MesaDTOResponse> listToDto(List<Mesa> mesas) {
        return mesas.stream().map(this::toDto).toList();
    }

    @Override
    public Mesa toEntity(MesaDTORequest mesaDTORequest) {
        if (mesaDTORequest == null) { return null; }
        return Mesa
                .builder()
                .numero(cambiarFormato(mesaDTORequest.numero()))
                .capacidad(mesaDTORequest.capacidad())
                .build();
    }

    public static String cambiarFormato(String texto) {
        if (texto == null || texto.isBlank()) { return texto; }
        return "mesa@" + texto;
    }

    public static String devolverFormato(String texto) {
        if (texto == null || texto.isBlank()) { return texto; }
        return texto.substring(texto.indexOf("@") + 1);
    }

}
