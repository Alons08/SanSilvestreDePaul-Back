package com.utp.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DocenteRequest {
    
    @NotNull(message = "El documento de identidad no puede ser nulo")
    private DocumentoIdentidadRequest documentoIdentidad;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;
    
    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    private String apellido;
    
    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;
    
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono debe contener solo números y tener entre 9 y 15 dígitos")
    private String telefono;
    
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;
}
