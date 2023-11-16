package com.stackdevs.authenticationservice.services.impl;

import com.stackdevs.authenticationservice.controllers.AuthenticationController;
import com.stackdevs.authenticationservice.models.dto.response.LoginResponseDTO;
import com.stackdevs.authenticationservice.security.JwtUtil;
import com.stackdevs.authenticationservice.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO authenticate(UsernamePasswordAuthenticationToken authenticationRequest) throws AuthenticationException {
       try {
           Authentication authentication = authenticationManager.authenticate(authenticationRequest);
           String token = jwtUtil.generateToken(authentication);
           return new LoginResponseDTO(token);
       }catch(AuthenticationException authenticationException) {
           logger.error("Login failed : ", authenticationException);
           return null;
       }
    }
}
