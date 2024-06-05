package com.serhatbalki.spring_security_jwt_example.service;

import com.serhatbalki.spring_security_jwt_example.dto.UserDto;
import com.serhatbalki.spring_security_jwt_example.dto.UserRequest;
import com.serhatbalki.spring_security_jwt_example.dto.UserResponse;
import com.serhatbalki.spring_security_jwt_example.entity.User;
import com.serhatbalki.spring_security_jwt_example.enums.Role;
import com.serhatbalki.spring_security_jwt_example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserResponse save(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nameSurname(userDto.getNameSurname())
                .role(Role.ADMIN).build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).role(user.getRole()).build();

    }

    public UserResponse auth(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).role(user.getRole()).build();
    }
}
