package com.phuongnh.personal.library_management_system.BookCopy;

import com.phuongnh.personal.library_management_system.Book.Book;
import com.phuongnh.personal.library_management_system.Book.BookRepository;
import com.phuongnh.personal.library_management_system.User.User;
import com.phuongnh.personal.library_management_system.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private BookCopyMapper bookCopyMapper;

    public List<BookCopyDTO> getAllBookCopies() {
        return bookCopyRepository.findAll().stream()
                .map(bookCopyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookCopyDTO getBookCopyById(UUID id) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
        return bookCopyMapper.toDTO(bookCopy);
    }

    public BookCopyDTO createBookCopy(BookCopyDTO bookCopyDTO) {
        Book book = bookRepository.findById(bookCopyDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        User borrower = bookCopyDTO.getBorrowerId() != null ? userRepository.findById(bookCopyDTO.getBorrowerId()).orElseThrow(() -> new RuntimeException("User not found")) : null;
        BookCopy bookCopy = bookCopyMapper.toEntity(bookCopyDTO, book, borrower);
        return bookCopyMapper.toDTO(bookCopyRepository.save(bookCopy));
    }

    public BookCopyDTO updateBookCopy(UUID id, BookCopyDTO bookCopyDTO) {
        BookCopy existingBookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
        Book book = bookRepository.findById(bookCopyDTO.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        User borrower = bookCopyDTO.getBorrowerId() != null ? userRepository.findById(bookCopyDTO.getBorrowerId()).orElseThrow(() -> new RuntimeException("User not found")) : null;
        BookCopy updatedBookCopy = bookCopyMapper.toEntity(bookCopyDTO, book, borrower);
        updatedBookCopy.setId(existingBookCopy.getId());
        return bookCopyMapper.toDTO(bookCopyRepository.save(updatedBookCopy));
    }

    public void deleteBookCopy(UUID id) {
        bookCopyRepository.deleteById(id);
    }

    public BookCopyDTO borrowBook(UUID id, UUID borrowerId) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
        if (bookCopy.getStatus() != BookCopyStatus.AVAILABLE) {
            throw new RuntimeException("Book copy is not available");
        }
        User borrower = userRepository.findById(borrowerId).orElseThrow(() -> new RuntimeException("User not found"));
        bookCopy.setStatus(BookCopyStatus.BORROWED);
        bookCopy.setBorrower(borrower);
        bookCopy.setBorrowDate(LocalDate.now());
        bookCopy.setReturnDate(LocalDate.now().plusDays(30));
        return bookCopyMapper.toDTO(bookCopyRepository.save(bookCopy));
    }

    public BookCopyDTO returnBook(UUID id) {
        BookCopy bookCopy = bookCopyRepository.findById(id).orElseThrow(() -> new RuntimeException("Book copy not found"));
        if (bookCopy.getStatus() != BookCopyStatus.BORROWED) {
            throw new RuntimeException("Book copy is not borrowed");
        }
        bookCopy.setStatus(BookCopyStatus.AVAILABLE);
        bookCopy.setBorrower(null);
        bookCopy.setBorrowDate(null);
        bookCopy.setReturnDate(null);
        return bookCopyMapper.toDTO(bookCopyRepository.save(bookCopy));
    }
}

