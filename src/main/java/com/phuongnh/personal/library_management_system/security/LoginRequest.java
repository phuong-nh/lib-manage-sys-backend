package com.phuongnh.personal.library_management_system.security;

import lombok.Data;

import jakarta.annotation.Nonnull;

@Data
public class LoginRequest {
    @Nonnull
    private String email;

    @Nonnull
    private String password;
}

