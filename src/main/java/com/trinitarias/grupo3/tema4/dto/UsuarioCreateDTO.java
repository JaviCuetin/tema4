package com.trinitarias.grupo3.tema4.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class UsuarioCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    private String email;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "Debes ser mayor de edad")
    @Max(value = 120, message = "La edad no es realista")
    private Integer edad;

    @Past(message = "La fecha de nacimiento debe ser pasada")
    private LocalDate fechaNacimiento;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}