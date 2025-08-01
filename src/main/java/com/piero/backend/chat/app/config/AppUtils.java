package com.piero.backend.chat.app.config;

public class AppUtils {
    public static String capitalizarTexto(String texto) {
        if (texto == null || texto.isBlank()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}
