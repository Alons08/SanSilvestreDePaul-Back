package com.utp.agregates.response;

import lombok.Data;
import lombok.Builder;
import com.utp.entity.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Data
@Builder
public class MatriculaResponse {
    
    private Long id;
    private String nombreAlumno;
    private String apellidoAlumno;
    private String numeroDocumentoAlumno;
    private String nombreGrado;
    private NivelEducativo nivelEducativo;
    private Year anoEscolar;
    private LocalDate fechaMatricula;
    private TipoMatricula tipoMatricula;
    private EstadoMatricula estado;
    private String observaciones;
    private String usuarioMatricula;
    private List<FechaPagoResponse> fechasPago;
}
