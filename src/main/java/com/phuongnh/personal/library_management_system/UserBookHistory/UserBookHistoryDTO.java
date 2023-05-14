package com.phuongnh.personal.library_management_system.UserBookHistory;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserBookHistoryDTO {
    private UUID userId;
    private UUID bookId;
    private UUID bookCopyId;
    private LocalDate readDate;
    private Integer rating;
    private String review;
}
