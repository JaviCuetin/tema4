package com.trinitarias.grupo3.tema4.validator;

import com.trinitarias.grupo3.tema4.dto.ContactoDTO;
import com.trinitarias.grupo3.tema4.dto.VideojuegoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MensajesI18nTest {

    private static final Locale ES = Locale.forLanguageTag("es");
    private static final Locale EN = Locale.forLanguageTag("en");

    private final VideojuegoValidator validator = new VideojuegoValidator();

    private static Properties cargar(String fichero) throws IOException {
        Properties p = new Properties();
        try (InputStream in = MensajesI18nTest.class.getResourceAsStream("/" + fichero)) {
            assertNotNull(in, "No existe " + fichero);
            p.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        }
        return p;
    }

    private static ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("messages");
        ms.setDefaultEncoding("UTF-8");
        ms.setFallbackToSystemLocale(false);
        return ms;
    }

    /** Juego con TODOS los campos mal, para que salte cada código posible. */
    private Errors erroresDeJuegoVacio() {
        VideojuegoDTO vacio = new VideojuegoDTO();
        Errors errors = new BeanPropertyBindingResult(vacio, "videojuego");
        validator.validate(vacio, errors);
        return errors;
    }

    private Errors erroresDeJuegoIncorrecto() {
        ContactoDTO c = new ContactoDTO();
        c.setNombre("Jo");
        c.setEmail("mal");
        VideojuegoDTO j = new VideojuegoDTO();
        j.setTitulo("A");
        j.setGenero("Inventado");
        j.setPlataforma("Inventada");
        j.setPrecio(-1.0);
        j.setPegi(99);
        j.setValoracion(99);
        j.setFechaLanzamiento(LocalDate.now().plusDays(5));
        j.setFechaProximoDlc(LocalDate.of(2020, 1, 1));
        j.setContacto(c);
        Errors errors = new BeanPropertyBindingResult(j, "videojuego");
        validator.validate(j, errors);
        return errors;
    }

    @Test
    void espanolIngleseYPorDefectoTienenLasMismasClaves() throws IOException {
        Set<String> es = cargar("messages_es.properties").stringPropertyNames();
        Set<String> en = cargar("messages_en.properties").stringPropertyNames();
        Set<String> porDefecto = cargar("messages.properties").stringPropertyNames();

        assertEquals(es, en, "messages_es y messages_en deben tener las mismas claves");
        assertEquals(es, porDefecto, "messages.properties debe tener las mismas claves que messages_es");
    }

    @Test
    void todosLosCodigosDelValidatorExistenEnAmbosIdiomas() throws IOException {
        Set<String> codigos = new HashSet<>();
        for (Errors errors : new Errors[]{erroresDeJuegoVacio(), erroresDeJuegoIncorrecto()}) {
            for (FieldError e : errors.getFieldErrors()) {
                codigos.add(e.getCode());
            }
        }
        assertFalse(codigos.isEmpty());

        Properties es = cargar("messages_es.properties");
        Properties en = cargar("messages_en.properties");
        for (String codigo : codigos) {
            assertTrue(es.containsKey(codigo), "Falta en messages_es: " + codigo);
            assertTrue(en.containsKey(codigo), "Falta en messages_en: " + codigo);
        }
    }

    @Test
    void elMensajeCambiaSegunElIdiomaYRellenaLosParametros() {
        ResourceBundleMessageSource ms = messageSource();
        FieldError pegi = erroresDeJuegoIncorrecto().getFieldError("pegi");

        assertEquals("El PEGI debe estar entre 3 y 18", ms.getMessage(pegi, ES));
        assertEquals("PEGI must be between 3 and 18", ms.getMessage(pegi, EN));
    }

    @Test
    void unIdiomaNoSoportadoUsaElMensajeEnEspanolPorDefecto() {
        ResourceBundleMessageSource ms = messageSource();
        FieldError titulo = erroresDeJuegoVacio().getFieldError("titulo");

        assertEquals("El título es obligatorio", ms.getMessage(titulo, Locale.FRENCH));
    }
}
