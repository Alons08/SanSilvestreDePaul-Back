package com.utp.agregates.response;

import lombok.Data;
import lombok.Builder;
import com.utp.entity.Genero;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AlumnoInfoResponse {
    
    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private String direccion;
    private List<MatriculaResponse> matriculas;
    private List<HorarioResponse> horarios;
}
