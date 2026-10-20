package com.trinitarias.grupo3.tema4.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ValidacionHandler {

    private final MessageSource messageSource;

    public ValidacionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejar(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        // Si un campo incumple varias reglas, se muestran todos los mensajes
        ex.getBindingResult().getFieldErrors()
          .forEach(e -> errores.merge(e.getField(), e.getDefaultMessage(), (a, b) -> a + " · " + b));

        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("errores", errores);
        return ResponseEntity.badRequest().body(body);
    }

    // JSON mal formado o con tipos incorrectos (ej: un texto donde va un número o una fecha)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(HttpMessageNotReadableException ex, Locale locale) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("errores", Map.of("general", messageSource.getMessage("json.invalido", null, locale)));
        return ResponseEntity.badRequest().body(body);
    }
}
