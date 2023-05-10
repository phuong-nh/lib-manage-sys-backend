package com.phuongnh.personal.library_management_system.Author;

import com.phuongnh.personal.library_management_system.Content.Content;
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
    private String surname;
    private Boolean isSurnameFirst;
    private String imgsrc;
    private Content authorBio;
}
