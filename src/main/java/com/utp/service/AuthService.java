package com.utp.service;

import com.utp.agregates.request.LoginRequest;
import com.utp.agregates.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

}