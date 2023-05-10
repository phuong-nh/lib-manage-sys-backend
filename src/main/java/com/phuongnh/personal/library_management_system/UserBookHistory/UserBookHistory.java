package com.phuongnh.personal.library_management_system.UserBookHistory;

import com.phuongnh.personal.library_management_system.User.User;
import com.phuongnh.personal.library_management_system.User.UserBookHistoryID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;

import com.phuongnh.personal.library_management_system.Book.Book;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_book_history")
public class UserBookHistory {

    @EmbeddedId
    private UserBookHistoryID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "read_date", nullable = false)
    private LocalDate readDate;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review")
    private String review;
}

