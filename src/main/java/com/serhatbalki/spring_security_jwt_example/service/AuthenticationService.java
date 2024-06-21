package com.serhatbalki.spring_security_jwt_example.service;

import com.serhatbalki.spring_security_jwt_example.dto.RefreshTokenRequest;
import com.serhatbalki.spring_security_jwt_example.dto.UserDto;
import com.serhatbalki.spring_security_jwt_example.dto.UserRequest;
import com.serhatbalki.spring_security_jwt_example.dto.UserResponse;
import com.serhatbalki.spring_security_jwt_example.entity.Token;
import com.serhatbalki.spring_security_jwt_example.entity.User;
import com.serhatbalki.spring_security_jwt_example.enums.Role;
import com.serhatbalki.spring_security_jwt_example.repository.TokenRepository;
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

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserResponse save(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nameSurname(userDto.getNameSurname())
                .role(Role.USER).build();
        userRepository.save(user);
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        tokenRepository.save(token);
        return UserResponse.builder().accessToken(accessToken).refreshToken(refreshToken).role(user.getRole()).build();
    }

    public UserResponse auth(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        tokenRepository.save(token);
        return UserResponse.builder().accessToken(accessToken).refreshToken(refreshToken).role(user.getRole()).build();
    }

    public UserResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.findUsername(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findByUsername(username).orElseThrow();
        if (jwtService.tokenControl(refreshTokenRequest.getRefreshToken(), user)) {
            var accessToken = jwtService.generateAccessToken(user);
            Token token = Token.builder()
                    .accessToken(accessToken)
                    .build();
            tokenRepository.save(token);
            return UserResponse.builder().accessToken(accessToken).refreshToken(refreshTokenRequest.getRefreshToken()).role(user.getRole()).build();
        }
        return null;
    }

    public void clearToken(String accessToken) {
        tokenRepository.deleteById(accessToken);
    }
}
