package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.dto.ContentDTO;
import com.phuongnh.personal.library_management_system.exception.*;
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
    private ContentService contentService;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
        return BookMapper.toDTO(book);
    }

    public List<BookCopyStatus> getCopiesStatus(UUID bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId.toString()));
        return book.getCopies().stream()
                .map(BookCopy::getStatus)
                .collect(Collectors.toList());
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);

        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new IsbnNotProvidedException();
        } else {
            Book existingBook = bookRepository.findByIsbn(book.getIsbn()).orElse(null);
            if (existingBook != null) {
                throw new IsbnAlreadyExistsException();
            }
        }

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());
            if (authors.size() != bookDTO.getAuthorIds().size()) {
                throw new AuthorIdsInvalidOrNotFoundException();
            }
            book.setAuthors(authors);
        } else {
            throw new NoAuthorIdsProvidedException();
        }

        if (bookDTO.getCategoryIds() != null && !bookDTO.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(bookDTO.getCategoryIds());
            if (categories.size() != bookDTO.getCategoryIds().size()) {
                throw new CategoryIdsInvalidOrNotFoundException();
            }
            book.setCategories(categories);
        } else {
            throw new NoCategoryIdsProvidedException();
        }

        bookRepository.save(book);

        if (bookDTO.getBookBio() != null) {
            ContentDTO bookBio = bookDTO.getBookBio();
            bookBio.setContentType(ContentType.BOOK_BIO);
            book.setBookBio(contentService.createContent(bookBio));
        } else {
            ContentDTO defaultBookBio = new ContentDTO();
            defaultBookBio.setContentType(ContentType.BOOK_BIO);
            defaultBookBio.setTitle("Book bio");
            defaultBookBio.setContent("This is the default book bio");
            book.setBookBio(contentService.createContent(defaultBookBio));
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

        return BookMapper.toDTO(book);
    }

    public BookDTO updateBook(UUID id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));

        book.setTitle(bookDTO.getTitle() != null ? bookDTO.getTitle() : book.getTitle());
        book.setIsbn(bookDTO.getIsbn() != null ? bookDTO.getIsbn() : book.getIsbn());
        book.setPublisher(bookDTO.getPublisher() != null ? bookDTO.getPublisher() : book.getPublisher());
        book.setPublishedDate(bookDTO.getPublishedDate() != null ? bookDTO.getPublishedDate() : book.getPublishedDate());
        book.setImgsrc(bookDTO.getImgsrc() != null ? bookDTO.getImgsrc() : book.getImgsrc());

        if (bookDTO.getAuthorIds() != null && !bookDTO.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(bookDTO.getAuthorIds());
            if (authors.size() != bookDTO.getAuthorIds().size()) {
                throw new AuthorIdsInvalidOrNotFoundException();
            }
            book.setAuthors(authors);
        }

        if (bookDTO.getCategoryIds() != null && !bookDTO.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(bookDTO.getCategoryIds());
            if (categories.size() != bookDTO.getCategoryIds().size()) {
                throw new CategoryIdsInvalidOrNotFoundException();
            }
            book.setCategories(categories);
        }

        Integer existingNumberOfCopies = book.getCopies().size();
        Integer newNumberOfCopies = bookDTO.getNumberOfCopies();
        if (!Objects.equals(newNumberOfCopies, existingNumberOfCopies) && newNumberOfCopies != null) {
            if (newNumberOfCopies > existingNumberOfCopies) {
                // Add new copies
                for (int i = 0; i < newNumberOfCopies - existingNumberOfCopies; i++) {
                    BookCopy bookCopy = new BookCopy();
                    bookCopy.setBook(book);
                    bookCopy.setStatus(BookCopyStatus.AVAILABLE);
                    bookCopyRepository.save(bookCopy);
                }
            } else {
                List<BookCopy> availableCopies = book.getCopies().stream()
                        .filter(copy -> copy.getStatus() == BookCopyStatus.AVAILABLE)
                        .toList();

                int copiesToRemove = existingNumberOfCopies - newNumberOfCopies;
                if (availableCopies.size() < copiesToRemove) {
                    throw new NotEnoughAvailableCopiesException();
                }
                for (int i = 0; i < copiesToRemove; i++) {
                    bookCopyRepository.delete(availableCopies.get(i));
                }
            }
        }

        if (bookDTO.getBookBio() != null && book.getBookBio() != null) {
            contentService.updateContent(book.getBookBio().getId(), bookDTO.getBookBio());
        }

        return BookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO deleteBook(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
        bookRepository.delete(book);
        return BookMapper.toDTO(book);
    }

    public BookDTO addCopies(UUID id, int amount) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
        List<BookCopy> copies = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            BookCopy bookCopy = new BookCopy();
            bookCopy.setBook(book);
            bookCopy.setStatus(BookCopyStatus.AVAILABLE);
            bookCopyRepository.save(bookCopy);
            copies.add(bookCopy);
        }

        book.getCopies().addAll(copies);
        bookRepository.save(book);

        return BookMapper.toDTO(book);
    }
}
