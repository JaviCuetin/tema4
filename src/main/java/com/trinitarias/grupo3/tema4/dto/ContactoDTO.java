package com.trinitarias.grupo3.tema4.dto;

/** Datos de la persona que registra el juego. Se valida en VideojuegoValidator. */
public class ContactoDTO {

    private String nombre;
    private String email;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
