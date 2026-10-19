package com.trinitarias.grupo3.tema4.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Ejemplo de @Positive, @Future y @Valid (validacion en cascada del objeto anidado).
 */
public class CitaDTO {

    @Positive(message = "La duracion debe ser mayor que 0")
    private int duracionMinutos;

    @Future(message = "La fecha de la cita debe estar en el futuro")
    private LocalDate fechaCita;

    @NotNull(message = "El paciente es obligatorio")
    @Valid
    private PacienteDTO paciente;

    public CitaDTO() {
    }

    public CitaDTO(int duracionMinutos, LocalDate fechaCita, PacienteDTO paciente) {
        this.duracionMinutos = duracionMinutos;
        this.fechaCita = fechaCita;
        this.paciente = paciente;
    }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public LocalDate getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDate fechaCita) { this.fechaCita = fechaCita; }
    public PacienteDTO getPaciente() { return paciente; }
    public void setPaciente(PacienteDTO paciente) { this.paciente = paciente; }
}
