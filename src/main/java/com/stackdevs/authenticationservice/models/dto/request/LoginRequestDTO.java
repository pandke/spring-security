package com.stackdevs.authenticationservice.models.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;


public record LoginRequestDTO(String username, String password) {
}
