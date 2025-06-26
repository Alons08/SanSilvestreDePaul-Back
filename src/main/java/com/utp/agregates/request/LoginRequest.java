package com.utp.agregates.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder //construir objetos...
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    String username;
    String password;

}