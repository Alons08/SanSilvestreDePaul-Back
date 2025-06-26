package com.utp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grados")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del grado no puede estar vacío")
    @Size(max = 50, message = "El nombre del grado no puede exceder los 50 caracteres")
    @Column(name = "nombre_grado", nullable = false, length = 50)
    private String nombreGrado;

    @NotNull(message = "El nivel educativo no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private NivelEducativo nivel;

    @OneToMany(mappedBy = "grado")
    private List<Aula> aulas;

    @OneToMany(mappedBy = "grado")
    private List<GradoCurso> cursos;

    @OneToMany(mappedBy = "grado")
    private List<Matricula> matriculas;
}