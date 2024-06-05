package com.serhatbalki.spring_security_jwt_example.controller;

import com.serhatbalki.spring_security_jwt_example.dto.UserDto;
import com.serhatbalki.spring_security_jwt_example.dto.UserRequest;
import com.serhatbalki.spring_security_jwt_example.dto.UserResponse;
import com.serhatbalki.spring_security_jwt_example.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
