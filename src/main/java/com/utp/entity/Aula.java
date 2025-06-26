package com.utp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aulas")
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del aula no puede estar vacío")
    @Size(max = 50, message = "El nombre del aula no puede exceder los 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @NotNull(message = "El grado no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @NotNull(message = "El docente no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    @Positive(message = "La capacidad debe ser un número positivo")
    private Integer capacidad;

    @NotNull(message = "La hora de inicio no puede ser nula")
    @Column(name = "hora_inicio", nullable = false)
    private Time horarioInicio;

    @NotNull(message = "La hora de fin no puede ser nula")
    @Column(name = "hora_fin", nullable = false)
    private Time horarioFin;
}