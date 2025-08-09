package com.piero.backend.chat.app.chatbot.controller;

import com.piero.backend.chat.app.chatbot.model.ConversacionChat;
import com.piero.backend.chat.app.config.SecurityUtils;
import com.piero.backend.chat.app.menu.model.Categoria;
import com.piero.backend.chat.app.menu.model.ItemMenu;
import com.piero.backend.chat.app.menu.service.CategoriaService;
import com.piero.backend.chat.app.menu.service.ItemMenuService;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTOResponse;
import com.piero.backend.chat.app.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class ChatController {
    private final CategoriaService categoriaService;
    private final ItemMenuService itemMenuService;
    private final UsuarioService usuarioService;

    private final Map<String, ConversacionChat> sesiones = new ConcurrentHashMap<>();

    @PostMapping
    public String conversar(@RequestBody String mensajeUsuario) {
        String correo = SecurityUtils.getCorreoUsuarioAutenticado();
        if (correo == null) {
            return "No autorizado. Inicia sesión para usar el chatbot.";
        }

        UsuarioDTOResponse usuario = usuarioService.obtenerUsuarioDtoPorCorreo(correo);

        ConversacionChat sesion = sesiones.computeIfAbsent(correo, k -> new ConversacionChat());

        String respuesta;

        switch (sesion.getPaso()) {
            case 0 -> {
                List<Categoria> categorias = categoriaService.obtenerCategoriasActivas();
                if (categorias.isEmpty()) {
                    return "No hay categorías disponibles en este momento.";
                }
                respuesta = "Hola " + usuario.nombre() + ", ¿qué tipo de comida prefieres? Estas son las categorías disponibles:\n" +
                        categorias.stream()
                                .map(c -> c.getId() + ". " + c.getNombre())
                                .collect(Collectors.joining("\n"));
                sesion.setPaso(1);
            }
            case 1 -> {
                try {
                    Short categoriaId = Short.parseShort(mensajeUsuario.trim());
                    Optional<Categoria> cat = categoriaService.buscarPorId(categoriaId);
                    if (cat.isEmpty() || !cat.get().getActivo()) {
                        respuesta = "Categoría no válida o inactiva. Intenta con otro número.";
                    } else {
                        sesion.setCategoriaId(categoriaId);
                        sesion.setPaso(2);
                        respuesta = "¿Cuál es tu presupuesto máximo? (por ejemplo: 25.00)";
                    }
                } catch (NumberFormatException e) {
                    respuesta = "Por favor, ingresa un número de categoría válido.";
                }
            }
            case 2 -> {
                try {
                    Double presupuesto = Double.parseDouble(mensajeUsuario.trim());
                    sesion.setPresupuestoMax(presupuesto);

                    List<ItemMenu> recomendados = itemMenuService
                            .buscarPorCategoriaYPrecio(sesion.getCategoriaId(), presupuesto);

                    if (recomendados.isEmpty()) {
                        respuesta = "No se encontraron platos dentro de ese presupuesto. ¿Deseas intentar con otro monto?";
                    } else {
                        respuesta = "Gracias " + usuario.nombre() + ", aquí tienes algunas recomendaciones:\n" +
                                recomendados.stream()
                                        .map(p -> "🍽 " + p.getNombre() + " - S/ " + p.getPrecio())
                                        .collect(Collectors.joining("\n"));
                    }

                    // Fin de la conversación, se limpia la sesión
                    sesiones.remove(correo);
                } catch (NumberFormatException e) {
                    respuesta = "Por favor, ingresa un presupuesto válido (ej: 20.00)";
                }
            }
            default -> {
                respuesta = "¿Quieres otra recomendación? Escribe cualquier cosa para comenzar de nuevo.";
                sesiones.remove(correo);
            }
        }

        return respuesta;
    }

}
