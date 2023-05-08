package com.phuongnh.personal.library_management_system.BookCopy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    public List<BookCopy> getAllBookCopies() {
        return bookCopyRepository.findAll();
    }

    public BookCopy getBookCopyById(UUID id) {
        return bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
    }

    public BookCopy createBookCopy(BookCopy bookCopy) {
        return bookCopyRepository.save(bookCopy);
    }

    public BookCopy updateBookCopy(UUID id, BookCopy bookCopy) {
        BookCopy existingBookCopy = getBookCopyById(id);
        existingBookCopy.setBook(bookCopy.getBook());
        existingBookCopy.setStatus(bookCopy.getStatus());
        existingBookCopy.setBorrower(bookCopy.getBorrower());
        existingBookCopy.setBorrowDate(bookCopy.getBorrowDate());
        existingBookCopy.setReturnDate(bookCopy.getReturnDate());
        return bookCopyRepository.save(existingBookCopy);
    }

    public void deleteBookCopy(UUID id) {
        bookCopyRepository.deleteById(id);
    }
}
