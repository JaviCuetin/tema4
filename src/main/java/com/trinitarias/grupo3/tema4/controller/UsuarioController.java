package com.trinitarias.grupo3.tema4.controller;

import com.trinitarias.grupo3.tema4.dto.UsuarioCreateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final List<UsuarioCreateDTO> usuarios = new ArrayList<>();

    @GetMapping
    public List<UsuarioCreateDTO> listar() {
        return usuarios;
    }

    @PostMapping
    public ResponseEntity<UsuarioCreateDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        usuarios.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}