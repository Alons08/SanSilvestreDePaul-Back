package com.utp.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradoCursoRequest {
    @NotNull(message = "El ID del grado no puede ser nulo")
    private Long gradoId;
    
    @NotNull(message = "El ID del curso no puede ser nulo")
    private Long cursoId;
    
    @PositiveOrZero(message = "Las horas semanales deben ser un número positivo o cero")
    private Integer horasSemanales;
}
