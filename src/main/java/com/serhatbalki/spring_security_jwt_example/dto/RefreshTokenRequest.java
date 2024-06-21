package com.serhatbalki.spring_security_jwt_example.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {

    private String refreshToken;
}
