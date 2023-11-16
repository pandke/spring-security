package com.stackdevs.authenticationservice.security;

import com.stackdevs.authenticationservice.models.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtEncoder jwtEncoder;

    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = getClaims(authentication);

        JwtClaimsSet claimsSet = buildJwtClaims(claims, authentication.getName());

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    protected static Map<String, Object> getClaims(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        User user = (User) authentication.getPrincipal();

        claims.put("authorities", scope);
        claims.put("user_id", user.getUserId());
        claims.put("email",  user.getEmail());
        claims.put("first_name", user.getFirstName());
        claims.put("last_name", user.getLastName());
        return claims;
    }


    protected JwtClaimsSet buildJwtClaims(Map<String, Object> customClaims, String subject) {
        Instant now = Instant.now();

        Instant expirationTime = now.plusSeconds(3600);

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expirationTime)
                .subject(subject);
        // Add custom claims one by one
        for (Map.Entry<String, Object> entry : customClaims.entrySet()) {
            claimsBuilder.claim(entry.getKey(), entry.getValue());
        }
        return claimsBuilder.build();
    }

}