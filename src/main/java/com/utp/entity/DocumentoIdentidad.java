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
@Table(name = "documentos_identidad",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tipo_documento", "numero_documento"}))
public class DocumentoIdentidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de documento no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El numero de documento no puede estar vacío")
    @Size(min = 8, max = 12, message = "El numero de documento debe tener entre 8 y 12 caracteres")
    @Column(name = "numero_documento", nullable = false, length = 12)
    private String numeroDocumento;
}