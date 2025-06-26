package com.utp.agregates.request;

import com.utp.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {

    private String username;
    private String password;
    private Role role;

}