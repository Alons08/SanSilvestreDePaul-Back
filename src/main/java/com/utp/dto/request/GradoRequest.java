package com.utp.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.utp.entity.NivelEducativo;

@Data
public class GradoRequest {
    
    @NotBlank(message = "El nombre del grado no puede estar vacío")
    @Size(max = 50, message = "El nombre del grado no puede exceder los 50 caracteres")
    private String nombreGrado;
    
    @NotNull(message = "El nivel educativo no puede ser nulo")
    private NivelEducativo nivel;
}
