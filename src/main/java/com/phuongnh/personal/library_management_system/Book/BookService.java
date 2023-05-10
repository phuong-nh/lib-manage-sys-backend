package com.phuongnh.personal.library_management_system.Book;

import com.phuongnh.personal.library_management_system.Author.Author;
import com.phuongnh.personal.library_management_system.Author.AuthorRepository;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopy;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopyRepository;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopyStatus;
import com.phuongnh.personal.library_management_system.Category.Category;
import com.phuongnh.personal.library_management_system.Category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        book = bookRepository.save(book);

        if (bookDTO.getNumberOfCopies() > 0) {
            for (int i = 0; i < bookDTO.getNumberOfCopies(); i++) {
                BookCopy bookCopy = new BookCopy();
                bookCopy.setBook(book);
                bookCopy.setStatus(BookCopyStatus.AVAILABLE);
                bookCopyRepository.save(bookCopy);
            }
        }

        return bookMapper.toDTO(book);
    }

    public BookDTO updateBook(UUID id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        Book updatedBook = bookMapper.updateEntityFromDTO(bookDTO, existingBook);

        int existingNumberOfCopies = existingBook.getCopies().size();
        int newNumberOfCopies = bookDTO.getNumberOfCopies();
        if (newNumberOfCopies != existingNumberOfCopies) {
            if (newNumberOfCopies > existingNumberOfCopies) {
                // Add new copies
                for (int i = 0; i < newNumberOfCopies - existingNumberOfCopies; i++) {
                    BookCopy bookCopy = new BookCopy();
                    bookCopy.setBook(existingBook);
                    bookCopy.setStatus(BookCopyStatus.AVAILABLE);
                    bookCopyRepository.save(bookCopy);
                }
            } else {
                // Remove extra copies (only remove AVAILABLE copies)
                List<BookCopy> availableCopies = existingBook.getCopies().stream()
                        .filter(copy -> copy.getStatus() == BookCopyStatus.AVAILABLE)
                        .collect(Collectors.toList());

                int copiesToRemove = existingNumberOfCopies - newNumberOfCopies;
                for (int i = 0; i < copiesToRemove && i < availableCopies.size(); i++) {
                    bookCopyRepository.delete(availableCopies.get(i));
                }
            }
        }
        return bookMapper.toDTO(bookRepository.save(updatedBook));
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteAllById(List.of(id));
    }
}
