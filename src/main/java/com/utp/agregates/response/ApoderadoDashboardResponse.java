package com.utp.agregates.response;

import lombok.Data;
import lombok.Builder;
import java.util.List;

@Data
@Builder
public class ApoderadoDashboardResponse {
    
    private String nombreApoderado;
    private String apellidoApoderado;
    private List<AlumnoInfoResponse> alumnos;
}
