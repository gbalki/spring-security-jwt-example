package com.serhatbalki.spring_security_jwt_example.repository;

import com.serhatbalki.spring_security_jwt_example.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
}
