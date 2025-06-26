package com.utp.agregates.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    //sin id y sin rol
    String username;
    String password;
    String firstname;
    String lastname;
    String country;

}
