package com.phuongnh.personal.library_management_system.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private UUID id;
    private String isbn;
    private String title;
    private String description;
    private String publisher;
    private List<UUID> authorIds;
    private List<UUID> categoryIds;
    private LocalDate publishedDate;
    private int numberOfCopies;
    private String imgsrc;
}
