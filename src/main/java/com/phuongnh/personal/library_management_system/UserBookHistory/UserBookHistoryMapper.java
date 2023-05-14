package com.phuongnh.personal.library_management_system.UserBookHistory;

import com.phuongnh.personal.library_management_system.Book.Book;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopy;
import com.phuongnh.personal.library_management_system.User.User;

import org.springframework.stereotype.Component;

@Component
public class UserBookHistoryMapper {

    public static UserBookHistoryDTO toDTO(UserBookHistory userBookHistory) {
        UserBookHistoryDTO dto = new UserBookHistoryDTO();
        dto.setUserId(userBookHistory.getUser().getId());
        dto.setBookId(userBookHistory.getBook().getId());
        dto.setBookCopyId(userBookHistory.getBookCopy().getId());
        dto.setReadDate(userBookHistory.getReadDate());
        dto.setRating(userBookHistory.getRating());
        dto.setReview(userBookHistory.getReview());
        return dto;
    }

    public static UserBookHistory toEntity(UserBookHistoryDTO dto, User user, Book book, BookCopy bookCopy) {
        UserBookHistory userBookHistory = new UserBookHistory();
        userBookHistory.setId(new UserBookHistoryID(user.getId(), book.getId()));
        userBookHistory.setUser(user);
        userBookHistory.setBook(book);
        userBookHistory.setBookCopy(bookCopy);
        userBookHistory.setReadDate(dto.getReadDate());
        userBookHistory.setRating(dto.getRating());
        userBookHistory.setReview(dto.getReview());
        return userBookHistory;
    }
}
