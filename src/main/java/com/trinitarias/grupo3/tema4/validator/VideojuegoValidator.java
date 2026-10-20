package com.trinitarias.grupo3.tema4.validator;

import com.trinitarias.grupo3.tema4.dto.ContactoDTO;
import com.trinitarias.grupo3.tema4.dto.VideojuegoDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;

/**
 * Validador personalizado de Spring para el formulario de videojuegos.
 * Al llevar @Component, Spring lo registra como bean y se inyecta en el controlador.
 *
 * i18n: aquí NO hay textos de error. Cada error lleva un código (ej: "precio.positivo")
 * y, si hace falta, parámetros ({0}, {1}). El texto final se busca en
 * messages_es.properties / messages_en.properties según el idioma de la petición.
 */
@Component
public class VideojuegoValidator implements Validator {

    private static final String REGEX_EMAIL = "^[\\w.+-]+@[\\w-]+\\.[\\w.]+$";

    // Límites: se cambian aquí y el mensaje los recibe como {0} y {1}
    static final int TITULO_MIN = 2, TITULO_MAX = 50;
    static final int PEGI_MIN = 3, PEGI_MAX = 18;
    static final int VALORACION_MIN = 0, VALORACION_MAX = 10;
    static final int CONTACTO_NOMBRE_MIN = 3, CONTACTO_NOMBRE_MAX = 30;

    static final List<String> GENEROS = List.of(
            "Acción", "Aventura", "RPG", "Deportes", "Estrategia", "Terror", "Plataformas", "Simulación");
    static final List<String> PLATAFORMAS = List.of(
            "PC", "PlayStation 5", "Xbox Series", "Nintendo Switch", "Móvil");

    /** Indica a Spring qué clases puede validar este Validator. */
    @Override
    public boolean supports(Class<?> clazz) {
        return VideojuegoDTO.class.isAssignableFrom(clazz);
    }

    /**
     * Lógica de validación. Cada error se añade al objeto Errors
     * con el nombre del campo, un código de error y (opcional) parámetros.
     */
    @Override
    public void validate(Object target, Errors errors) {
        VideojuegoDTO juego = (VideojuegoDTO) target;

        // Título: obligatorio (null, vacío o solo espacios) y longitud
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo", "titulo.vacio");
        if (juego.getTitulo() != null && !juego.getTitulo().isBlank()) {
            int longitud = juego.getTitulo().trim().length();
            if (longitud < TITULO_MIN || longitud > TITULO_MAX) {
                errors.rejectValue("titulo", "titulo.longitud",
                        new Object[]{TITULO_MIN, TITULO_MAX}, null);
            }
        }

        // Género y plataforma: obligatorios y de entre las opciones permitidas
        if (juego.getGenero() == null || juego.getGenero().isBlank()) {
            errors.rejectValue("genero", "genero.vacio");
        } else if (!GENEROS.contains(juego.getGenero())) {
            errors.rejectValue("genero", "genero.invalido");
        }
        if (juego.getPlataforma() == null || juego.getPlataforma().isBlank()) {
            errors.rejectValue("plataforma", "plataforma.vacia");
        } else if (!PLATAFORMAS.contains(juego.getPlataforma())) {
            errors.rejectValue("plataforma", "plataforma.invalida");
        }

        // Precio: obligatorio y positivo
        if (juego.getPrecio() == null) {
            errors.rejectValue("precio", "precio.vacio");
        } else if (juego.getPrecio() <= 0) {
            errors.rejectValue("precio", "precio.positivo");
        }

        // PEGI: obligatorio y dentro del rango
        if (juego.getPegi() == null) {
            errors.rejectValue("pegi", "pegi.vacio");
        } else if (juego.getPegi() < PEGI_MIN || juego.getPegi() > PEGI_MAX) {
            errors.rejectValue("pegi", "pegi.rango", new Object[]{PEGI_MIN, PEGI_MAX}, null);
        }

        // Valoración: obligatoria y dentro del rango
        if (juego.getValoracion() == null) {
            errors.rejectValue("valoracion", "valoracion.vacia");
        } else if (juego.getValoracion() < VALORACION_MIN || juego.getValoracion() > VALORACION_MAX) {
            errors.rejectValue("valoracion", "valoracion.rango",
                    new Object[]{VALORACION_MIN, VALORACION_MAX}, null);
        }

        // Fecha de lanzamiento: obligatoria y pasada
        if (juego.getFechaLanzamiento() == null) {
            errors.rejectValue("fechaLanzamiento", "fechaLanzamiento.vacia");
        } else if (!juego.getFechaLanzamiento().isBefore(LocalDate.now())) {
            errors.rejectValue("fechaLanzamiento", "fechaLanzamiento.pasada");
        }

        // Próximo DLC: opcional, pero si se rellena tiene que ser futura
        if (juego.getFechaProximoDlc() != null && !juego.getFechaProximoDlc().isAfter(LocalDate.now())) {
            errors.rejectValue("fechaProximoDlc", "fechaProximoDlc.futura");
        }

        // Contacto (objeto anidado): sus campos se validan con la ruta "contacto.xxx"
        ContactoDTO contacto = juego.getContacto();
        if (contacto == null) {
            errors.rejectValue("contacto", "contacto.vacio");
            return;
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contacto.nombre", "contacto.nombre.vacio");
        if (contacto.getNombre() != null && !contacto.getNombre().isBlank()) {
            int longitud = contacto.getNombre().trim().length();
            if (longitud < CONTACTO_NOMBRE_MIN || longitud > CONTACTO_NOMBRE_MAX) {
                errors.rejectValue("contacto.nombre", "contacto.nombre.longitud",
                        new Object[]{CONTACTO_NOMBRE_MIN, CONTACTO_NOMBRE_MAX}, null);
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contacto.email", "contacto.email.vacio");
        if (contacto.getEmail() != null && !contacto.getEmail().isBlank()
                && !contacto.getEmail().matches(REGEX_EMAIL)) {
            errors.rejectValue("contacto.email", "contacto.email.formato");
        }
    }
}
