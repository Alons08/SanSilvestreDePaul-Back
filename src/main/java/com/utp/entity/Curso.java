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
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(max = 100, message = "El nombre del curso no puede exceder los 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "curso")
    private List<GradoCurso> grados;
}