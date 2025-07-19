package com.utp.dto.response;

import com.utp.entity.Personal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String tipoDocumento;
    private String numeroDocumento;
    private String username;
    private String role;
    private Boolean estado;

    public static PersonalResponse fromEntity(Personal personal) {
        PersonalResponse response = new PersonalResponse();
        response.setId(personal.getId());
        response.setNombre(personal.getNombre());
        response.setApellido(personal.getApellido());
        response.setEmail(personal.getEmail());
        response.setTipoDocumento(personal.getDocumentoIdentidad().getTipoDocumento().toString());
        response.setNumeroDocumento(personal.getDocumentoIdentidad().getNumeroDocumento());
        response.setUsername(personal.getUser().getUsername());
        response.setRole(personal.getUser().getRole().toString());
        response.setEstado(personal.getEstado());
        return response;
    }

    public static List<PersonalResponse> fromEntityList(List<Personal> personalList) {
        return personalList.stream()
                .map(PersonalResponse::fromEntity)
                .toList();
    }
}
