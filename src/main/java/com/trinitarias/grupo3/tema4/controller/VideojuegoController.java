package com.trinitarias.grupo3.tema4.controller;

import com.trinitarias.grupo3.tema4.dto.VideojuegoDTO;
import com.trinitarias.grupo3.tema4.validator.VideojuegoValidator;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final List<VideojuegoDTO> videojuegos = new CopyOnWriteArrayList<>();
    private final VideojuegoValidator validator;
    private final MessageSource messageSource;

    // Spring inyecta el @Component VideojuegoValidator y el MessageSource (i18n)
    public VideojuegoController(VideojuegoValidator validator, MessageSource messageSource) {
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @GetMapping
    public List<VideojuegoDTO> listar() {
        return videojuegos;
    }

    // Spring rellena "locale" según la cabecera Accept-Language (ver LocaleConfig)
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody VideojuegoDTO juego, Locale locale) {
        // Errors recoge los fallos que va detectando el validator
        Errors errors = new BeanPropertyBindingResult(juego, "videojuego");
        validator.validate(juego, errors);

        if (errors.hasErrors()) {
            Map<String, String> mensajes = new LinkedHashMap<>();
            // getMessage(error, locale) busca el código del error en messages_xx.properties
            errors.getFieldErrors().forEach(e ->
                    mensajes.merge(e.getField(), messageSource.getMessage(e, locale), (a, b) -> a + " · " + b));

            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", 400);
            body.put("error", "Bad Request");
            body.put("errores", mensajes);
            return ResponseEntity.badRequest().body(body);
        }

        videojuegos.add(juego);
        return ResponseEntity.status(HttpStatus.CREATED).body(juego);
    }
}
