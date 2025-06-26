package com.utp.agregates.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.utp.entity.TipoDocumento;

@Data
public class DocumentoIdentidadRequest {
    
    @NotNull(message = "El tipo de documento no puede ser nulo")
    private TipoDocumento tipoDocumento;
    
    @NotBlank(message = "El número de documento no puede estar vacío")
    @Size(min = 8, max = 12, message = "El número de documento debe tener entre 8 y 12 caracteres")
    private String numeroDocumento;
}
