package com.phuongnh.personal.library_management_system.UserBookHistory;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserBookHistoryID implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "book_id")
    private UUID bookId;
}