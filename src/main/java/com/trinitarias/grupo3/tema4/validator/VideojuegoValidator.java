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
 */
@Component
public class VideojuegoValidator implements Validator {

    private static final String REGEX_EMAIL = "^[\\w.+-]+@[\\w-]+\\.[\\w.]+$";

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
     * con el nombre del campo, un código de error y un mensaje por defecto.
     */
    @Override
    public void validate(Object target, Errors errors) {
        VideojuegoDTO juego = (VideojuegoDTO) target;

        // Título: obligatorio (null, vacío o solo espacios) y longitud
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titulo",
                "titulo.vacio", "El título es obligatorio");
        if (juego.getTitulo() != null && !juego.getTitulo().isBlank()) {
            int longitud = juego.getTitulo().trim().length();
            if (longitud < 2 || longitud > 50) {
                errors.rejectValue("titulo", "titulo.longitud",
                        "El título debe tener entre 2 y 50 caracteres");
            }
        }

        // Género y plataforma: obligatorios y de entre las opciones permitidas
        if (juego.getGenero() == null || juego.getGenero().isBlank()) {
            errors.rejectValue("genero", "genero.vacio", "Elige un género");
        } else if (!GENEROS.contains(juego.getGenero())) {
            errors.rejectValue("genero", "genero.invalido", "El género elegido no es válido");
        }
        if (juego.getPlataforma() == null || juego.getPlataforma().isBlank()) {
            errors.rejectValue("plataforma", "plataforma.vacia", "Elige una plataforma");
        } else if (!PLATAFORMAS.contains(juego.getPlataforma())) {
            errors.rejectValue("plataforma", "plataforma.invalida", "La plataforma elegida no es válida");
        }

        // Precio: obligatorio y positivo
        if (juego.getPrecio() == null) {
            errors.rejectValue("precio", "precio.vacio", "El precio es obligatorio");
        } else if (juego.getPrecio() <= 0) {
            errors.rejectValue("precio", "precio.positivo", "El precio debe ser mayor que 0");
        }

        // PEGI: obligatorio y entre 3 y 18
        if (juego.getPegi() == null) {
            errors.rejectValue("pegi", "pegi.vacio", "La edad mínima (PEGI) es obligatoria");
        } else if (juego.getPegi() < 3 || juego.getPegi() > 18) {
            errors.rejectValue("pegi", "pegi.rango", "El PEGI debe estar entre 3 y 18");
        }

        // Valoración: obligatoria y entre 0 y 10
        if (juego.getValoracion() == null) {
            errors.rejectValue("valoracion", "valoracion.vacia", "La valoración es obligatoria");
        } else if (juego.getValoracion() < 0 || juego.getValoracion() > 10) {
            errors.rejectValue("valoracion", "valoracion.rango", "La valoración debe estar entre 0 y 10");
        }

        // Fecha de lanzamiento: obligatoria y pasada
        if (juego.getFechaLanzamiento() == null) {
            errors.rejectValue("fechaLanzamiento", "fechaLanzamiento.vacia",
                    "La fecha de lanzamiento es obligatoria");
        } else if (!juego.getFechaLanzamiento().isBefore(LocalDate.now())) {
            errors.rejectValue("fechaLanzamiento", "fechaLanzamiento.pasada",
                    "La fecha de lanzamiento debe estar en el pasado");
        }

        // Próximo DLC: opcional, pero si se rellena tiene que ser futura
        if (juego.getFechaProximoDlc() != null && !juego.getFechaProximoDlc().isAfter(LocalDate.now())) {
            errors.rejectValue("fechaProximoDlc", "fechaProximoDlc.futura",
                    "La fecha del próximo DLC debe estar en el futuro");
        }

        // Contacto (objeto anidado): sus campos se validan con la ruta "contacto.xxx"
        ContactoDTO contacto = juego.getContacto();
        if (contacto == null) {
            errors.rejectValue("contacto", "contacto.vacio", "Los datos de contacto son obligatorios");
            return;
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contacto.nombre",
                "contacto.nombre.vacio", "El nombre del contacto es obligatorio");
        if (contacto.getNombre() != null && !contacto.getNombre().isBlank()) {
            int longitud = contacto.getNombre().trim().length();
            if (longitud < 3 || longitud > 30) {
                errors.rejectValue("contacto.nombre", "contacto.nombre.longitud",
                        "El nombre del contacto debe tener entre 3 y 30 caracteres");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contacto.email",
                "contacto.email.vacio", "El email es obligatorio");
        if (contacto.getEmail() != null && !contacto.getEmail().isBlank()
                && !contacto.getEmail().matches(REGEX_EMAIL)) {
            errors.rejectValue("contacto.email", "contacto.email.formato",
                    "El email no tiene un formato válido (ej: usuario@gmail.com)");
        }
    }
}
