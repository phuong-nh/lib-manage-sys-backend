package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.exception.BookCopyNotFoundException;
import com.phuongnh.personal.library_management_system.exception.BookNotFoundException;
import com.phuongnh.personal.library_management_system.mapper.BookCopyMapper;
import com.phuongnh.personal.library_management_system.dto.BookCopyDTO;
import com.phuongnh.personal.library_management_system.model.Book;
import com.phuongnh.personal.library_management_system.model.BookCopy;
import com.phuongnh.personal.library_management_system.model.BookCopyStatus;
import com.phuongnh.personal.library_management_system.repository.BookCopyRepository;
import com.phuongnh.personal.library_management_system.repository.BookRepository;
import com.phuongnh.personal.library_management_system.model.User;
import com.phuongnh.personal.library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public List<BookCopyDTO> getAllBookCopies() {
        return bookCopyRepository.findAll().stream()
                .map(BookCopyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookCopyDTO getBookCopyById(UUID id) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new BookCopyNotFoundException(id.toString()));
        return BookCopyMapper.toDTO(bookCopy);
    }

    public BookCopyDTO createBookCopy(BookCopyDTO bookCopyDTO) {
        Book book = bookRepository.findById(bookCopyDTO.getBookId()).orElseThrow(() -> new BookNotFoundException(bookCopyDTO.getBookId().toString()));
        User borrower = bookCopyDTO.getBorrowerId() != null ? userRepository.findById(bookCopyDTO.getBorrowerId()).orElseThrow(() -> new RuntimeException("User not found")) : null;
        BookCopy bookCopy = BookCopyMapper.toEntity(bookCopyDTO, book, borrower);
        return BookCopyMapper.toDTO(bookCopyRepository.save(bookCopy));
    }

    public BookCopyDTO updateBookCopy(UUID id, BookCopyDTO bookCopyDTO) {
        BookCopy existingBookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new BookCopyNotFoundException(id.toString()));
        Book book = bookRepository.findById(bookCopyDTO.getBookId()).orElseThrow(() -> new BookNotFoundException(bookCopyDTO.getBookId().toString()));
        User borrower = bookCopyDTO.getBorrowerId() != null ? userRepository.findById(bookCopyDTO.getBorrowerId()).orElseThrow(() -> new RuntimeException("User not found")) : null;
        BookCopy updatedBookCopy = BookCopyMapper.toEntity(bookCopyDTO, book, borrower);
        updatedBookCopy.setId(existingBookCopy.getId());
        return BookCopyMapper.toDTO(bookCopyRepository.save(updatedBookCopy));
    }

    public void deleteBookCopy(UUID id) {
        bookCopyRepository.deleteById(id);
    }

    public BookCopyDTO borrowBook(UUID id) {
        UUID borrowerId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
        if (bookCopy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new RuntimeException("Book copy is not available");
        }
        User borrower = userRepository.findById(borrowerId).orElseThrow(() -> new RuntimeException("User not found"));
        bookCopy.setStatus(BookCopyStatus.BORROWED);
        bookCopy.setBorrower(borrower);
        bookCopy.setBorrowDate(LocalDate.now());
        bookCopy.setReturnDate(LocalDate.now().plusDays(30));

        // TODO: add log to UserBookHistory

        return BookCopyMapper.toDTO(bookCopyRepository.save(bookCopy));
    }

    public BookCopyDTO returnBook(UUID id) {
        UUID borrowerId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
        if (bookCopy.getStatus() != BookCopyStatus.BORROWED) {
            throw new RuntimeException("Book copy is not borrowed");
        } else if (!bookCopy.getBorrower().getId().equals(borrowerId)) {
            throw new RuntimeException("Book copy is borrowed by another user");
        }

        bookCopy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopy.setBorrower(null);
        bookCopy.setBorrowDate(null);
        bookCopy.setReturnDate(null);

        // TODO: add log to UserBookHistory

        return BookCopyMapper.toDTO(bookCopyRepository.save(bookCopy));
    }
}

