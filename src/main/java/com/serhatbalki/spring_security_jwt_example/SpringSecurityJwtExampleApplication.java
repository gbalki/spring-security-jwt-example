package com.serhatbalki.spring_security_jwt_example;

import com.serhatbalki.spring_security_jwt_example.entity.User;
import com.serhatbalki.spring_security_jwt_example.enums.Role;
import com.serhatbalki.spring_security_jwt_example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityJwtExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner createInitialAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return (args) -> {
            try {
                User user = User.builder()
                        .nameSurname("Serhat Balkı")
                        .username("Serhat.b")
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println("Error occurred while creating initial admin: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}

