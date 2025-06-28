package com.utp.agregates.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import com.utp.entity.TipoDocumento;
import com.utp.entity.Genero;
import java.time.LocalDate;

@Data
public class AlumnoRequest {
    
    @NotNull(message = "El tipo de documento no puede ser nulo")
    private TipoDocumento tipoDocumento;
    
    @NotBlank(message = "El número de documento no puede estar vacío")
    @Size(min = 8, max = 12, message = "El número de documento debe tener entre 8 y 12 caracteres")
    private String numeroDocumento;
    
    @NotNull(message = "El ID del apoderado no puede ser nulo")
    private Long apoderadoId;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String apellido;
    
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;
    
    @NotNull(message = "El género no puede ser nulo")
    private Genero genero;
    
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
    
    private Boolean tieneDiscapacidad = false;
    private String diagnosticoMedico;
}
