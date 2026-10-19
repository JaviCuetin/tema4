package com.trinitarias.grupo3.tema4.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

/**
 * Ejemplo de @Positive, @Future y @Valid (validacion en cascada del objeto anidado).
 */
public class CitaDTO {

    // @Positive: numero mayor que 0
    @Positive(message = "La duracion debe ser mayor que 0")
    private int duracionMinutos;

    // @Future: la fecha de la cita debe ser posterior a hoy
    @Future(message = "La fecha de la cita debe estar en el futuro")
    private LocalDate fechaCita;

    // @Valid: hace que tambien se comprueben las validaciones de PacienteDTO
    // @NotNull: sin el, un paciente null se saltaria la validacion en cascada
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
