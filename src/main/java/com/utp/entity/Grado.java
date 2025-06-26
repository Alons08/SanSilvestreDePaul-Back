package com.utp.entity;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 50)
    private String nombre;

    @Enumerated(EnumType.STRING)
    private NivelEducativo nivel;

    @OneToMany(mappedBy = "grado")
    private List<Aula> aulas;

    @OneToMany(mappedBy = "grado")
    private List<GradoCurso> cursos;

    @OneToMany(mappedBy = "grado")
    private List<Matricula> matriculas;  // Nueva relación agregada
}