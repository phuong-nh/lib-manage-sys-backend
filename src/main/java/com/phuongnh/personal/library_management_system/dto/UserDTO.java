package com.phuongnh.personal.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String givenName;
    private String surName;
    private Boolean isGivenSurName;
    private String email;
    private String role = "USER";
    private String imgsrc;
    private Boolean isBanned = false;
}
