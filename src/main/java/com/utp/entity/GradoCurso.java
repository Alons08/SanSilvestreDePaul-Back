package com.utp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grado_cursos")
public class GradoCurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El grado no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @NotNull(message = "El curso no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @PositiveOrZero(message = "Las horas semanales deben ser un numero positivo o cero")
    @Column(name = "horas_semanales")
    private Integer horasSemanales;
}