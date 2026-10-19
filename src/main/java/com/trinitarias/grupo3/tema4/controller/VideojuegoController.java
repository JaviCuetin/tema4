package com.trinitarias.grupo3.tema4.controller;

import com.trinitarias.grupo3.tema4.dto.VideojuegoDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final List<VideojuegoDTO> videojuegos = new CopyOnWriteArrayList<>();

    @GetMapping
    public List<VideojuegoDTO> listar() {
        return videojuegos;
    }

    // @Valid: si algún dato no cumple las reglas, ValidacionHandler responde 400 y no se guarda
    @PostMapping
    public ResponseEntity<VideojuegoDTO> crear(@Valid @RequestBody VideojuegoDTO juego) {
        videojuegos.add(juego);
        return ResponseEntity.status(HttpStatus.CREATED).body(juego);
    }
}
