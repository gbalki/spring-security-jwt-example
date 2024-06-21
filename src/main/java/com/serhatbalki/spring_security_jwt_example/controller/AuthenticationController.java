package com.serhatbalki.spring_security_jwt_example.controller;

import com.serhatbalki.spring_security_jwt_example.dto.RefreshTokenRequest;
import com.serhatbalki.spring_security_jwt_example.dto.UserDto;
import com.serhatbalki.spring_security_jwt_example.dto.UserRequest;
import com.serhatbalki.spring_security_jwt_example.dto.UserResponse;
import com.serhatbalki.spring_security_jwt_example.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private  AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> save(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.save(userDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserResponse> signIn(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(authenticationService.auth(userRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(name = "Authorization")String authorization){
        String accessToken = authorization.substring(7);
        authenticationService.clearToken(accessToken);
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<UserResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
