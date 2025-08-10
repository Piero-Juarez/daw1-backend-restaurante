package com.piero.backend.chat.app.ordenes.mapper;

import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTORequest;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTOResponse;
import com.piero.backend.chat.app.ordenes.model.Mesa;

import java.util.List;

public interface MesaMapper {

    MesaDTOResponse toDto(Mesa mesa);
    List<MesaDTOResponse> listToDto(List<Mesa> mesas);
    Mesa toEntity(MesaDTORequest mesaDTORequest);

}
