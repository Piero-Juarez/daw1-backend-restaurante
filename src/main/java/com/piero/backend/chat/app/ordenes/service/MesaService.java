package com.piero.backend.chat.app.ordenes.service;

import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTORequest;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTOResponse;

import java.util.List;

public interface MesaService {
    List<MesaDTOResponse> listarMesas();
    List<MesaDTOResponse> listarMesasDisponibles();
    MesaDTOResponse guardarMesa(MesaDTORequest mesaDTOResponse);
    MesaDTOResponse actualizarMesa(Short id, MesaDTORequest mesaDTOResponse);
    MesaDTOResponse obtenerMesaPorId(Short id);
    void eliminarMesa(Short idMesa);
}
