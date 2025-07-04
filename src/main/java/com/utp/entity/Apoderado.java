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
@Table(name = "apoderados")
public class Apoderado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El documento de identidad no puede ser nulo")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documento_identidad_id", nullable = false)
    private DocumentoIdentidad documentoIdentidad;

    @NotNull(message = "El usuario no puede ser nulo")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede exceder los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String apellido;

    @NotNull(message = "El parentesco no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private Parentesco parentesco;

    private String direccion;
    private String departamento;
    private String provincia;
    private String distrito;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "El teléfono debe contener solo números y tener entre 9 y 15 dígitos")
    @Column(nullable = false, length = 15)
    private String telefono;

    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    @Column(length = 100)
    private String email;

    @Column(name = "lugar_trabajo")
    private String lugarTrabajo;
    private String cargo;

    @Builder.Default
    @Column(nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "apoderado")
    private List<Alumno> alumnos;
}