package com.utp.agregates.response;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FechaPagoResponse {
    
    private Long id;
    private String descripcion;
    private LocalDate fechaVencimiento;
    private BigDecimal monto;
    private LocalDate fechaPago;
    private Boolean pagado;
    private String numeroRecibo;
    private String observaciones;
}
