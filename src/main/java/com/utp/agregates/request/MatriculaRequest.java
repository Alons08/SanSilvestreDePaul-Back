package com.utp.agregates.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.utp.entity.TipoMatricula;
import java.time.Year;

@Data
public class MatriculaRequest {
    
    @NotNull(message = "El ID del alumno no puede ser nulo")
    private Long alumnoId;
    
    @NotNull(message = "El ID del grado no puede ser nulo") 
    private Long gradoId;
    
    @NotNull(message = "El año escolar no puede ser nulo")
    private Year anoEscolar;
    
    @NotNull(message = "El tipo de matrícula no puede ser nulo")
    private TipoMatricula tipoMatricula;
    
    @Size(max = 255, message = "Las observaciones no pueden exceder los 255 caracteres")
    private String observaciones;
}
