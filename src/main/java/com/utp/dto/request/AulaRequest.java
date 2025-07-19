package com.utp.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalTime;

@Data
public class AulaRequest {
    
    @NotBlank(message = "El nombre del aula no puede estar vacío")
    @Size(max = 50, message = "El nombre del aula no puede exceder los 50 caracteres")
    private String nombre;
    
    @NotNull(message = "El ID del grado no puede ser nulo")
    private Long gradoId;
    
    @NotNull(message = "El ID del docente no puede ser nulo")
    private Long docenteId;
    
    @Positive(message = "La capacidad debe ser un número positivo")
    private Integer capacidad;
    
    @NotNull(message = "La hora de inicio no puede ser nula")
    private LocalTime horarioInicio;
    
    @NotNull(message = "La hora de fin no puede ser nula")
    private LocalTime horarioFin;
}
