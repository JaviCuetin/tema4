package com.trinitarias.grupo3.tema4.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Ejemplo de anotaciones de validacion sobre los campos de un DTO.
 */
public class PacienteDTO {

    // @NotBlank: no puede ser null, vacio ni solo espacios ("   " no es valido)
    // @Size: longitud entre 3 y 20 caracteres
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    private String nombre;

    // @NotNull: debe tener algun valor (pero "" o "  " si serian validos)
    @NotNull(message = "El apodo no puede ser null")
    private String apodo;

    // @Min / @Max: limites del valor numerico
    @Min(value = 18, message = "Debes tener al menos 18 anios")
    @Max(value = 100, message = "La edad maxima es 100")
    private Integer edad;

    // @Email: formato de correo (null se considera valido; combinar con @NotBlank si es obligatorio)
    @Email(message = "El email no tiene un formato valido")
    private String email;

    // @Past: la fecha debe ser anterior a hoy
    @Past(message = "La fecha de nacimiento debe estar en el pasado")
    private LocalDate fechaNacimiento;

    public PacienteDTO() {
    }

    public PacienteDTO(String nombre, String apodo, Integer edad, String email, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apodo = apodo;
        this.edad = edad;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
