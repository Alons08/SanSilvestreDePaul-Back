package com.utp.service;

import com.utp.dto.request.LoginRequest;
import com.utp.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

}