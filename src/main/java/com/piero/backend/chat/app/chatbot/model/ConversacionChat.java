package com.piero.backend.chat.app.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversacionChat {
    private Short categoriaId;
    private Double presupuestoMax;
    private int paso = 0;
}
