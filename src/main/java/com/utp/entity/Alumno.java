package com.utp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_identidad_id", nullable = false)
    private DocumentoIdentidad documentoIdentidad;

    @ManyToOne
    @JoinColumn(name = "apoderado_id", nullable = false)
    private Apoderado apoderado;  // Relación directa con Apoderado

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

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
    private List<Matricula> matriculas;

}