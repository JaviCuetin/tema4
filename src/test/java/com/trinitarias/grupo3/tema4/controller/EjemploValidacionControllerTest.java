package com.trinitarias.grupo3.tema4.controller;

import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EjemploValidacionControllerTest {

    @Test
    void cadaCasoDaElResultadoEsperado() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            List<EjemploValidacionController.Caso> casos =
                    new EjemploValidacionController(factory.getValidator()).ejecutarCasos();

            assertFalse(casos.isEmpty());
            for (EjemploValidacionController.Caso caso : casos) {
                boolean esperadoOk = caso.descripcion().contains("-> OK");
                assertEquals(esperadoOk, caso.valido(),
                        caso.anotacion() + " | " + caso.descripcion() + " | " + caso.errores());
            }
        }
    }

    @Test
    void validCascadaAlPacienteAnidado() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            var caso = new EjemploValidacionController(factory.getValidator()).ejecutarCasos().stream()
                    .filter(c -> c.anotacion().equals("@Valid") && !c.valido())
                    .findFirst().orElseThrow();

            assertTrue(caso.errores().stream().anyMatch(e -> e.startsWith("paciente.edad")));
            assertTrue(caso.errores().stream().anyMatch(e -> e.startsWith("paciente.nombre")));
        }
    }
}
