package com.phuongnh.personal.library_management_system.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String givenName;
    private String surName;
    private Boolean isGivenSurName;
    private String email;
    private String password;
}
