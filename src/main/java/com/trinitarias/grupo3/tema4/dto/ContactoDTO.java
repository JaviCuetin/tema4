package com.trinitarias.grupo3.tema4.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Datos de la persona que registra el juego (objeto anidado, se valida con @Valid). */
public class ContactoDTO {

    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(min = 3, max = 30, message = "El nombre del contacto debe tener entre 3 y 30 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido (ej: usuario@gmail.com)")
    private String email;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
