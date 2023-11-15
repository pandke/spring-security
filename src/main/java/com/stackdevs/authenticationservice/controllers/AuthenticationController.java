package com.stackdevs.authenticationservice.controllers;

import com.stackdevs.authenticationservice.models.dto.request.LoginRequestDTO;
import com.stackdevs.authenticationservice.models.dto.response.ApiResponse;
import com.stackdevs.authenticationservice.models.dto.response.LoginResponseDTO;
import com.stackdevs.authenticationservice.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> authenticate(@ModelAttribute LoginRequestDTO loginRequest) {
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        LoginResponseDTO loginResponse = authenticationService.authenticate(authenticationRequest);

        ApiResponse<LoginResponseDTO> authenticationApiResponse = new ApiResponse<>();
        authenticationApiResponse.setMessage("Request was successful");
        authenticationApiResponse.setData(loginResponse);
        authenticationApiResponse.setStatusCode(HttpServletResponse.SC_OK);

        return ResponseEntity.ok(authenticationApiResponse);
    }
}
