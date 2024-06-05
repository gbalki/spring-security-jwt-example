package com.serhatbalki.spring_security_jwt_example.dto;

import com.serhatbalki.spring_security_jwt_example.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String token;

    private Role role;
}
