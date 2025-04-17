package com.example.spring.basicboardv2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDTO {
    private boolean isLoggined;
    private String userId;
    private String username;
    private String token; // access token > localstorage(chrome)
}
