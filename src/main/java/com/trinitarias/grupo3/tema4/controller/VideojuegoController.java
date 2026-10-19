package com.trinitarias.grupo3.tema4.controller;

import com.trinitarias.grupo3.tema4.dto.VideojuegoDTO;
import com.trinitarias.grupo3.tema4.validator.VideojuegoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final List<VideojuegoDTO> videojuegos = new CopyOnWriteArrayList<>();
    private final VideojuegoValidator validator;

    // Spring inyecta el @Component VideojuegoValidator
    public VideojuegoController(VideojuegoValidator validator) {
        this.validator = validator;
    }

    @GetMapping
    public List<VideojuegoDTO> listar() {
        return videojuegos;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody VideojuegoDTO juego) {
        // Errors recoge los fallos que va detectando el validator
        Errors errors = new BeanPropertyBindingResult(juego, "videojuego");
        validator.validate(juego, errors);

        if (errors.hasErrors()) {
            Map<String, String> mensajes = new LinkedHashMap<>();
            errors.getFieldErrors().forEach(e ->
                    mensajes.merge(e.getField(), e.getDefaultMessage(), (a, b) -> a + " · " + b));

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
