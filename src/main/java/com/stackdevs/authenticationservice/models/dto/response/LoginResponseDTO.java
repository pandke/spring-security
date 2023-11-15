package com.stackdevs.authenticationservice.models.dto.response;

import com.stackdevs.authenticationservice.models.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;


public record LoginResponseDTO( Authentication authentication) {
}
