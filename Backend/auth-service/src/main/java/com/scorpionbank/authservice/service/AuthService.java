package com.scorpionbank.authservice.service;

import com.scorpionbank.authservice.dto.AuthResponse;
import com.scorpionbank.authservice.dto.LoginRequest;
import com.scorpionbank.authservice.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}
