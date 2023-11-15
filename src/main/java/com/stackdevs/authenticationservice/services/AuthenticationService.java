package com.stackdevs.authenticationservice.services;

import com.stackdevs.authenticationservice.models.dto.response.LoginResponseDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthenticationService {
    LoginResponseDTO authenticate(UsernamePasswordAuthenticationToken authenticationRequest);
}
