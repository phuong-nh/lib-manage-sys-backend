package com.phuongnh.personal.library_management_system.BookCopy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopyDTO {

    private UUID id;
    private UUID bookId;
    private BookCopyStatus status;
    private UUID borrowerId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
