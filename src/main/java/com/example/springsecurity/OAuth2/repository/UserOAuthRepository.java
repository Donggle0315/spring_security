package com.example.springsecurity.OAuth2.repository;

import com.example.springsecurity.OAuth2.entity.UserOAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOAuthRepository extends JpaRepository<UserOAuthEntity, String> {
}
