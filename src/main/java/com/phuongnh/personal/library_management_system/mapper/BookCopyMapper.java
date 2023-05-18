package com.phuongnh.personal.library_management_system.mapper;

import com.phuongnh.personal.library_management_system.dto.BookCopyDTO;
import com.phuongnh.personal.library_management_system.model.Book;
import com.phuongnh.personal.library_management_system.model.BookCopy;
import com.phuongnh.personal.library_management_system.model.User;
import org.springframework.stereotype.Component;

@Component
public class BookCopyMapper {

    public static BookCopyDTO toDTO(BookCopy bookCopy) {
        BookCopyDTO dto = new BookCopyDTO();
        dto.setId(bookCopy.getId());
        dto.setBookId(bookCopy.getBook().getId());
        dto.setStatus(bookCopy.getStatus());
        dto.setBorrowerId(bookCopy.getBorrower() != null ? bookCopy.getBorrower().getId() : null);
        dto.setBorrowDate(bookCopy.getBorrowDate());
        dto.setReturnDate(bookCopy.getReturnDate());
        return dto;
    }

    public static BookCopy toEntity(BookCopyDTO dto, Book book, User borrower) {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(dto.getId());
        bookCopy.setBook(book);
        bookCopy.setStatus(dto.getStatus());
        bookCopy.setBorrower(borrower);
        bookCopy.setBorrowDate(dto.getBorrowDate());
        bookCopy.setReturnDate(dto.getReturnDate());
        return bookCopy;
    }
}
