package com.trinitarias.grupo3.tema4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * Decide en qué idioma (Locale) se responde a cada petición.
 * Lee la cabecera HTTP Accept-Language: el navegador la envía solo y la página
 * la fuerza cuando el usuario elige ES/EN en el selector.
 */
@Configuration
public class LocaleConfig {

    public static final Locale ESPANOL = Locale.forLanguageTag("es");
    public static final Locale INGLES = Locale.forLanguageTag("en");

    // El nombre del bean tiene que ser "localeResolver" para que Spring lo use
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(List.of(ESPANOL, INGLES));
        resolver.setDefaultLocale(ESPANOL); // idioma no soportado o sin cabecera -> español
        return resolver;
    }
}
