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
@Table(name = "docentes")
public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_identidad_id", nullable = false)
    private DocumentoIdentidad documentoIdentidad;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;

    @Builder.Default
    private Boolean estado = true;

    @OneToMany(mappedBy = "docente")
    private List<Aula> aulas;

}