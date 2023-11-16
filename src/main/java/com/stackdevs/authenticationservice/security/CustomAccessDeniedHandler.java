package com.stackdevs.authenticationservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackdevs.authenticationservice.models.dto.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiResponse<?> accessDeniedResponse = new ApiResponse<>();
        accessDeniedResponse.setStatusCode(HttpServletResponse.SC_FORBIDDEN);
        accessDeniedResponse.setMessage("Insufficient permission(s) to access this resource");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writer().writeValue(response.getOutputStream(), accessDeniedResponse);
    }
}
