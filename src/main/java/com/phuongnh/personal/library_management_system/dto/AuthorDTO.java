package com.phuongnh.personal.library_management_system.dto;

import com.phuongnh.personal.library_management_system.model.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private UUID id;
    private String givenName;
    private String surName;
    private Boolean isGivenSurName;
    private String imgsrc;
    private Content authorBio;
}
