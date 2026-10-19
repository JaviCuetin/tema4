package com.trinitarias.grupo3.tema4.dto;

import java.time.LocalDate;

/** Formulario de registro de un videojuego. Se valida en VideojuegoValidator. */
public class VideojuegoDTO {

    private String titulo;
    private String genero;
    private String plataforma;
    private Double precio;
    private Integer pegi;
    private Integer valoracion;
    private LocalDate fechaLanzamiento;
    private LocalDate fechaProximoDlc;
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
