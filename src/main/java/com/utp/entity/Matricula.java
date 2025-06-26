package com.utp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

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

    @NotNull(message = "El alumno no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @NotNull(message = "El grado no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @NotNull(message = "El año escolar no puede ser nulo")
    @Column(name = "ano_escolar", nullable = false)
    private Year anoEscolar;

    @NotNull(message = "La fecha de matricula no puede ser nula")
    @PastOrPresent(message = "La fecha de matricula debe ser hoy o una fecha pasada")
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_matricula")
    private TipoMatricula tipoMatricula;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EstadoMatricula estado = EstadoMatricula.Pendiente;

    // Usuario que realizó la matrícula (puede ser secretaria o apoderado)
    @ManyToOne
    @JoinColumn(name = "usuario_matricula_id")
    private User usuarioMatricula;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FechaPago> fechasPago;
}