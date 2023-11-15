package com.stackdevs.authenticationservice.models.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiResponse <T>{
    private String message;
    private int statusCode;
    private T data;
    private final Instant timestamp = Instant.now();
}
