package com.trinitarias.grupo3.tema4.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/** Formulario de registro de un videojuego: usa todas las anotaciones vistas en clase. */
public class VideojuegoDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 50, message = "El título debe tener entre 2 y 50 caracteres")
    private String titulo;

    @NotNull(message = "Elige un género")
    private String genero;

    @NotNull(message = "Elige una plataforma")
    private String plataforma;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    private Double precio;

    @NotNull(message = "La edad mínima (PEGI) es obligatoria")
    @Min(value = 3, message = "El PEGI mínimo es 3")
    @Max(value = 18, message = "El PEGI máximo es 18")
    private Integer pegi;

    @NotNull(message = "La valoración es obligatoria")
    @Min(value = 0, message = "La valoración mínima es 0")
    @Max(value = 10, message = "La valoración máxima es 10")
    private Integer valoracion;

    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    @Past(message = "La fecha de lanzamiento debe estar en el pasado")
    private LocalDate fechaLanzamiento;

    // Opcional: si se rellena, tiene que ser futura (null es válido)
    @Future(message = "La fecha del próximo DLC debe estar en el futuro")
    private LocalDate fechaProximoDlc;

    @NotNull(message = "Los datos de contacto son obligatorios")
    @Valid
    private ContactoDTO contacto;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getPegi() { return pegi; }
    public void setPegi(Integer pegi) { this.pegi = pegi; }
    public Integer getValoracion() { return valoracion; }
    public void setValoracion(Integer valoracion) { this.valoracion = valoracion; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }
    public LocalDate getFechaProximoDlc() { return fechaProximoDlc; }
    public void setFechaProximoDlc(LocalDate fechaProximoDlc) { this.fechaProximoDlc = fechaProximoDlc; }
    public ContactoDTO getContacto() { return contacto; }
    public void setContacto(ContactoDTO contacto) { this.contacto = contacto; }
}
