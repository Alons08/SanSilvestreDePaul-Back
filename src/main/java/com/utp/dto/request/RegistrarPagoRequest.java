package com.utp.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarPagoRequest {
    
    private LocalDate fechaPago;
    
    @Size(max = 50, message = "El número de recibo no puede exceder los 50 caracteres")
    private String numeroRecibo;
    
    @Size(max = 255, message = "Las observaciones no pueden exceder los 255 caracteres")
    private String observaciones;
}
