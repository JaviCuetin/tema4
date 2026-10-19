package com.trinitarias.grupo3.tema4.validator;

import com.trinitarias.grupo3.tema4.dto.ContactoDTO;
import com.trinitarias.grupo3.tema4.dto.VideojuegoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class VideojuegoValidatorTest {

    private final VideojuegoValidator validator = new VideojuegoValidator();

    private static VideojuegoDTO juegoValido() {
        ContactoDTO c = new ContactoDTO();
        c.setNombre("Javier");
        c.setEmail("javier@gmail.com");
        VideojuegoDTO j = new VideojuegoDTO();
        j.setTitulo("Zelda");
        j.setGenero("Aventura");
        j.setPlataforma("Nintendo Switch");
        j.setPrecio(59.99);
        j.setPegi(12);
        j.setValoracion(10);
        j.setFechaLanzamiento(LocalDate.of(2017, 3, 3));
        j.setFechaProximoDlc(LocalDate.now().plusDays(30));
        j.setContacto(c);
        return j;
    }

    private Errors validar(VideojuegoDTO juego) {
        Errors errors = new BeanPropertyBindingResult(juego, "videojuego");
        validator.validate(juego, errors);
        return errors;
    }

    private void assertError(String campo, Consumer<VideojuegoDTO> cambio) {
        VideojuegoDTO j = juegoValido();
        cambio.accept(j);
        assertNotNull(validar(j).getFieldError(campo), "Se esperaba error en " + campo);
    }

    @Test
    void juegoValidoNoTieneErrores() {
        assertFalse(validar(juegoValido()).hasErrors());
    }

    @Test
    void dlcEsOpcional() {
        VideojuegoDTO j = juegoValido();
        j.setFechaProximoDlc(null);
        assertFalse(validar(j).hasErrors());
    }

    @Test
    void supportsSoloVideojuegoDTO() {
        assertTrue(validator.supports(VideojuegoDTO.class));
        assertFalse(validator.supports(String.class));
    }

    @Test
    void detectaCadaDatoIncorrecto() {
        assertError("titulo", j -> j.setTitulo("   "));
        assertError("titulo", j -> j.setTitulo("A"));
        assertError("genero", j -> j.setGenero(null));
        assertError("genero", j -> j.setGenero("Inventado"));
        assertError("plataforma", j -> j.setPlataforma(null));
        assertError("precio", j -> j.setPrecio(null));
        assertError("precio", j -> j.setPrecio(0.0));
        assertError("pegi", j -> j.setPegi(2));
        assertError("pegi", j -> j.setPegi(19));
        assertError("valoracion", j -> j.setValoracion(11));
        assertError("valoracion", j -> j.setValoracion(-1));
        assertError("fechaLanzamiento", j -> j.setFechaLanzamiento(LocalDate.now().plusDays(1)));
        assertError("fechaProximoDlc", j -> j.setFechaProximoDlc(LocalDate.of(2020, 1, 1)));
        assertError("contacto.nombre", j -> j.getContacto().setNombre("Jo"));
        assertError("contacto.email", j -> j.getContacto().setEmail("usuario-gmail.com"));
        assertError("contacto", j -> j.setContacto(null));
    }
}
