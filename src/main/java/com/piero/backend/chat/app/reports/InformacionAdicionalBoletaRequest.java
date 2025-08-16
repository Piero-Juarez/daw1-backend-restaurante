package com.piero.backend.chat.app.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InformacionAdicionalBoletaRequest(
        @JsonProperty("monto_pagado") Double montoPagado
) { }
