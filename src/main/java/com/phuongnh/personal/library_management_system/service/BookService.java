package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.mapper.BookMapper;
import com.phuongnh.personal.library_management_system.dto.BookDTO;
import com.phuongnh.personal.library_management_system.model.*;
import com.phuongnh.personal.library_management_system.repository.*;
import com.phuongnh.personal.library_management_system.model.BookCopyStatus;
import com.phuongnh.personal.library_management_system.model.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.toDTO(book);
    }

    public List<BookCopyStatus> getBookCopyStatus(UUID bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getCopies().stream()
                .map(BookCopy::getStatus)
                .collect(Collectors.toList());
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());
            if (authors.size() != bookDTO.getAuthorIds().size()) {
                throw new RuntimeException("Some author IDs are invalid or not found");
            }
            book.setAuthors(authors);
        } else {
            throw new RuntimeException("At least one author ID must be provided");
        }

        if (bookDTO.getCategoryIds() != null && !bookDTO.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(bookDTO.getCategoryIds());
            if (categories.size() != bookDTO.getCategoryIds().size()) {
                throw new RuntimeException("Some category IDs are invalid or not found");
            }
            book.setCategories(categories);
        } else {
            throw new RuntimeException("At least one category ID must be provided");
        }

        bookRepository.save(book);

        if (bookDTO.getBookBio() == null) {
            Content defaultBookBio = new Content();
            defaultBookBio.setTitle("");
            defaultBookBio.setContent("");
            defaultBookBio.setContentType(ContentType.valueOf("BOOK_BIO"));
            contentRepository.save(defaultBookBio);
            book.setBookBio(defaultBookBio);
        }

        bookRepository.save(book);

        List<BookCopy> copies = new ArrayList<>();
        for (int i = 0; i < bookDTO.getNumberOfCopies(); i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(book);
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepository.save(bookCopy);
            copies.add(bookCopy);
        }

        book.setCopies(copies);
        bookRepository.save(book);

        return bookMapper.toDTO(book);
    }

    public BookDTO updateBook(UUID id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        Book updatedBook = bookMapper.updateEntityFromDTO(bookDTO, existingBook);

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());
            if (authors.size() != bookDTO.getAuthorIds().size()) {
                throw new RuntimeException("Some author IDs are invalid or not found");
            }
            updatedBook.setAuthors(authors);
        }

        if (bookDTO.getCategoryIds() != null && !bookDTO.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(bookDTO.getCategoryIds());
            if (categories.size() != bookDTO.getCategoryIds().size()) {
                throw new RuntimeException("Some category IDs are invalid or not found");
            }
            updatedBook.setCategories(categories);
        }

        Integer existingNumberOfCopies = existingBook.getCopies().size();
        Integer newNumberOfCopies = bookDTO.getNumberOfCopies();
        if (!Objects.equals(newNumberOfCopies, existingNumberOfCopies) && newNumberOfCopies != null) {
            if (newNumberOfCopies > existingNumberOfCopies) {
                // Add new copies
                for (int i = 0; i < newNumberOfCopies - existingNumberOfCopies; i++) {
                    BookCopy bookCopy = new BookCopy();
                    bookCopy.setBook(existingBook);
                    bookCopy.setStatus(BookCopyStatus.AVAILABLE);
                    bookCopyRepository.save(bookCopy);
                }
            } else {
                List<BookCopy> availableCopies = existingBook.getCopies().stream()
                        .filter(copy -> copy.getStatus() == BookCopyStatus.AVAILABLE)
                        .toList();

                int copiesToRemove = existingNumberOfCopies - newNumberOfCopies;
                for (int i = 0; i < copiesToRemove && i < availableCopies.size(); i++) {
                    bookCopyRepository.delete(availableCopies.get(i));
                }
            }
        }

        if (bookDTO.getBookBio() != null && updatedBook.getBookBio() != null) {
            Content bookBio = updatedBook.getBookBio();
            bookBio.setTitle(bookDTO.getBookBio().getTitle());
            bookBio.setContent(bookDTO.getBookBio().getContent());
            contentRepository.save(bookBio);
        }

        return bookMapper.toDTO(bookRepository.save(updatedBook));
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteAllById(List.of(id));
    }
}
