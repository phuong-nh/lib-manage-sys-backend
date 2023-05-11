package com.phuongnh.personal.library_management_system.Content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {

    private ContentType contentType;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime date;
}
