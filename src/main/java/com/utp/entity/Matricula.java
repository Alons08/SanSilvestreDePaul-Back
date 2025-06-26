package com.utp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.Year;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matriculas")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)  // Cambiado de aula_id a grado_id
    private Grado grado;  // Cambiado de Aula a Grado

    @Column(name = "ano_escolar", nullable = false)
    private Year anoEscolar;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_matricula")
    private TipoMatricula tipoMatricula;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoMatricula estado = EstadoMatricula.Pendiente;
}