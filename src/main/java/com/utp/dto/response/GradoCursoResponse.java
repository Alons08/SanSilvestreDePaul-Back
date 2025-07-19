package com.utp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradoCursoResponse {
    private Long id;
    private Long gradoId;
    private String gradoNombre;
    private String nivelEducativo;
    private Long cursoId;
    private String cursoNombre;
    private String cursoDescripcion;
    private Integer horasSemanales;
}
