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
@Table(name = "personal")
public class Personal {
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

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email no puede estar vacío")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Builder.Default
    @Column(nullable = false)
    private Boolean estado = true;
}
