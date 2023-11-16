package com.stackdevs.authenticationservice.exceptions;

import com.stackdevs.authenticationservice.models.dto.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ApiResponse<?> errorResponse = new ApiResponse<>();
    private final Logger logger  = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus( code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<ApiResponse<?>> handleCustomAuthenticationException(CustomAuthenticationException exception) {
        logger.error(exception.getMessage(), exception);

        errorResponse.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);
        errorResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
