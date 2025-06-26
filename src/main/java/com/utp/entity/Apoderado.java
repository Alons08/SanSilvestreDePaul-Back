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
@Table(name = "apoderados")
public class Apoderado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_identidad_id", nullable = false)
    private DocumentoIdentidad documentoIdentidad;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // Relación obligatoria

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Enumerated(EnumType.STRING)
    private Parentesco parentesco;

    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(length = 100)
    private String email;

    private String lugarTrabajo;
    private String cargo;

    @OneToMany(mappedBy = "apoderado")
    private List<Alumno> alumnos;
}