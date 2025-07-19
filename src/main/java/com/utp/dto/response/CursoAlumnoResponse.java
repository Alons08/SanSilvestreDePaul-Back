package com.utp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoAlumnoResponse {
    private Long id;
    private String nombreCurso;
    private String descripcionCurso;
    private Integer horasSemanales;
    private String nombreGrado;
    private String nivelEducativo;
    private String nombreAlumno;
    private String apellidoAlumno;
}
