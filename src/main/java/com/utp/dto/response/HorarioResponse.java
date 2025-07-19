package com.utp.dto.response;

import lombok.Data;
import lombok.Builder;
import java.time.LocalTime;

@Data
@Builder
public class HorarioResponse {
    
    private Long aulaId;
    private String nombreAula;
    private String nombreGrado;
    private String nombreDocente;
    private String apellidoDocente;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private Integer capacidad;
}
