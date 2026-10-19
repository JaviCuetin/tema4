package com.trinitarias.grupo3.tema4.controller;

import com.trinitarias.grupo3.tema4.dto.CitaDTO;
import com.trinitarias.grupo3.tema4.dto.PacienteDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/ejemplos")
public class EjemploValidacionController {

    /** Resultado de validar un caso de ejemplo. */
    public record Caso(String anotacion, String descripcion, boolean valido, Set<String> errores) {
    }

    private final Validator validator;

    public EjemploValidacionController(Validator validator) {
        this.validator = validator;
    }

    /**
     * @Valid en el parametro: Spring valida el body antes de entrar al metodo.
     * Si hay errores, ValidacionHandler responde 400 con el detalle por campo.
     */
    @PostMapping("/citas")
    public ResponseEntity<CitaDTO> crearCita(@Valid @RequestBody CitaDTO cita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cita);
    }

    /** Ejecuta casos que funcionan y casos que fallan, para cada anotacion. */
    @GetMapping("/demo")
    public List<Caso> demo() {
        return ejecutarCasos();
    }

    List<Caso> ejecutarCasos() {
        List<Caso> casos = new ArrayList<>();

        // @NotNull
        casos.add(validar("@NotNull", "apodo = \"\" (vacio pero no null) -> OK",
                paciente(p -> p.setApodo(""))));
        casos.add(validar("@NotNull", "apodo = null -> ERROR",
                paciente(p -> p.setApodo(null))));

        // @NotBlank
        casos.add(validar("@NotBlank", "nombre = \"Lucia\" -> OK",
                paciente(p -> p.setNombre("Lucia"))));
        casos.add(validar("@NotBlank", "nombre = \"   \" (solo espacios) -> ERROR",
                paciente(p -> p.setNombre("   "))));

        // @Size(min = 3, max = 20)
        casos.add(validar("@Size", "nombre de 5 caracteres -> OK",
                paciente(p -> p.setNombre("Pedro"))));
        casos.add(validar("@Size", "nombre de 2 caracteres -> ERROR",
                paciente(p -> p.setNombre("Jo"))));

        // @Min(18)
        casos.add(validar("@Min", "edad = 18 -> OK",
                paciente(p -> p.setEdad(18))));
        casos.add(validar("@Min", "edad = 17 -> ERROR",
                paciente(p -> p.setEdad(17))));

        // @Max(100)
        casos.add(validar("@Max", "edad = 100 -> OK",
                paciente(p -> p.setEdad(100))));
        casos.add(validar("@Max", "edad = 101 -> ERROR",
                paciente(p -> p.setEdad(101))));

        // @Email
        casos.add(validar("@Email", "email = usuario@gmail.com -> OK",
                paciente(p -> p.setEmail("usuario@gmail.com"))));
        casos.add(validar("@Email", "email = usuario-gmail.com (sin @) -> ERROR",
                paciente(p -> p.setEmail("usuario-gmail.com"))));

        // @Positive
        casos.add(validar("@Positive", "duracionMinutos = 30 -> OK",
                cita(c -> c.setDuracionMinutos(30))));
        casos.add(validar("@Positive", "duracionMinutos = 0 (no es > 0) -> ERROR",
                cita(c -> c.setDuracionMinutos(0))));

        // @Past
        casos.add(validar("@Past", "fechaNacimiento = 2000-05-01 -> OK",
                paciente(p -> p.setFechaNacimiento(LocalDate.of(2000, 5, 1)))));
        casos.add(validar("@Past", "fechaNacimiento = manana -> ERROR",
                paciente(p -> p.setFechaNacimiento(LocalDate.now().plusDays(1)))));

        // @Future
        casos.add(validar("@Future", "fechaCita = dentro de 7 dias -> OK",
                cita(c -> c.setFechaCita(LocalDate.now().plusDays(7)))));
        casos.add(validar("@Future", "fechaCita = ayer -> ERROR",
                cita(c -> c.setFechaCita(LocalDate.now().minusDays(1)))));

        // @Valid (cascada sobre el paciente anidado)
        casos.add(validar("@Valid", "cita con paciente correcto -> OK",
                cita(c -> { })));
        casos.add(validar("@Valid", "cita con paciente invalido (edad 5, nombre \"  \") -> ERROR en paciente.*",
                cita(c -> {
                    c.getPaciente().setEdad(5);
                    c.getPaciente().setNombre("  ");
                })));

        return casos;
    }

    private Caso validar(String anotacion, String descripcion, Object bean) {
        Set<String> errores = new TreeSet<>();
        for (ConstraintViolation<Object> v : validator.validate(bean)) {
            errores.add(v.getPropertyPath() + ": " + v.getMessage());
        }
        return new Caso(anotacion, descripcion, errores.isEmpty(), errores);
    }

    /** Paciente completamente valido que cada caso modifica en un solo campo. */
    private static PacienteDTO paciente(java.util.function.Consumer<PacienteDTO> cambio) {
        PacienteDTO p = new PacienteDTO("Maria", "Mari", 30, "maria@gmail.com", LocalDate.of(1995, 3, 10));
        cambio.accept(p);
        return p;
    }

    /** Cita completamente valida que cada caso modifica en un solo campo. */
    private static CitaDTO cita(java.util.function.Consumer<CitaDTO> cambio) {
        CitaDTO c = new CitaDTO(45, LocalDate.now().plusDays(3), paciente(p -> { }));
        cambio.accept(c);
        return c;
    }
}
