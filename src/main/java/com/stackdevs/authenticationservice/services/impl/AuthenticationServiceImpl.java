package com.stackdevs.authenticationservice.services.impl;

import com.stackdevs.authenticationservice.exceptions.CustomAuthenticationException;
import com.stackdevs.authenticationservice.models.dto.response.LoginResponseDTO;
import com.stackdevs.authenticationservice.security.jwtutils.JwtTokenGenerator;
import com.stackdevs.authenticationservice.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenGenerator jwtUtil;

    @Override
    public LoginResponseDTO authenticate(UsernamePasswordAuthenticationToken authenticationRequest) {
       try {
           Authentication authentication = authenticationManager.authenticate(authenticationRequest);
           String token = jwtUtil.generateToken(authentication);
           return new LoginResponseDTO(token);
       }catch(AuthenticationException authenticationException) {
           String message = authenticationException.getLocalizedMessage();
           if (authenticationException instanceof BadCredentialsException) {
              message = "Login failed. Invalid username or password";
           }

           if (authenticationException instanceof AccountExpiredException) {
               message = "Your account has expired. Contact administrator for support";
           }

           if (authenticationException instanceof LockedException) {
               // user has exceeded the number of password trials
               message ="Sorry your account has been locked.";
           }

           if (authenticationException instanceof CredentialsExpiredException) {
               message = "Your password has expired. Please change the password to access the system";
           }

           if (authenticationException instanceof DisabledException ) {
               message = "Your account has been disabled. Contact administrator for support";
           }

           if (authenticationException instanceof UsernameNotFoundException) {
               message = authenticationException.getMessage();
           }

           if (authenticationException instanceof InvalidBearerTokenException) {
               message = "Your access token is malformed. Please use a correctly formatted access token";
           }
           throw new CustomAuthenticationException(message, authenticationException);
       }
    }
}
