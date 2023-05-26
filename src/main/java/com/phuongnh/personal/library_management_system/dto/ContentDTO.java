package com.phuongnh.personal.library_management_system.dto;

import com.phuongnh.personal.library_management_system.model.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {
    private UUID id;
    private ContentType contentType;
    private String title;
    private String content;
    private String imgsrc;
    private Boolean showOnHomePage;
    private LocalDateTime date;
    private UUID authorId;
}
