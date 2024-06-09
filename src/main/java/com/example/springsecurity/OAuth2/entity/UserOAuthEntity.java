package com.example.springsecurity.OAuth2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserOAuthEntity {
    @Id
    private String userId;
    private String email;
    private String clientName;
}
