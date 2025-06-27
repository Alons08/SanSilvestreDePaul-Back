package com.utp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El documento de identidad no puede ser nulo")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_identidad_id", nullable = false)
    private DocumentoIdentidad documentoIdentidad;

    @ManyToOne
    @JoinColumn(name = "apoderado_id")
    private Apoderado apoderado;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String apellido;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull(message = "El género no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;

    @Builder.Default
    @Column(name = "tiene_discapacidad")
    private Boolean tieneDiscapacidad = false;

    @Lob
    @Column(name = "diagnostico_medico")
    private String diagnosticoMedico;

    @Builder.Default
    private Boolean estado = true;

    @OneToMany(mappedBy = "alumno")
    @JsonIgnore
    private List<Matricula> matriculas;
}