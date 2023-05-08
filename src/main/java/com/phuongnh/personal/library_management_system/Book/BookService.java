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

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(Book book) {
        if (book.getAuthorIds() != null && !book.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(book.getAuthorIds());
            if (authors.size() != book.getAuthorIds().size()) {
                throw new RuntimeException("Some author IDs are invalid or not found");
            }
            book.setAuthors(authors);
        } else {
            throw new RuntimeException("At least one author ID must be provided");
        }

        if (book.getCategoryIds() != null && !book.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(book.getCategoryIds());
            if (categories.size() != book.getCategoryIds().size()) {
                throw new RuntimeException("Some category IDs are invalid or not found");
            }
            book.setCategories(categories);
        } else {
            throw new RuntimeException("At least one category ID must be provided");
        }

        book = bookRepository.save(book);

        if (book.getNumberOfCopies() > 0) {
            for (int i = 0; i < book.getNumberOfCopies(); i++) {
                BookCopy bookCopy = new BookCopy();
                bookCopy.setBook(book);
                bookCopy.setStatus(BookCopyStatus.AVAILABLE);
                bookCopyRepository.save(bookCopy);
            }
        }

        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setIsbn(book.getIsbn());
        existingBook.setTitle(book.getTitle());
        existingBook.setDescription(book.getDescription());
        existingBook.setPublisher(book.getPublisher());
        existingBook.setAuthors(book.getAuthors());
        existingBook.setCategories(book.getCategories());
        existingBook.setPublishedDate(book.getPublishedDate());
        existingBook.setCopies(book.getCopies());
        existingBook.setImgsrc(book.getImgsrc());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(UUID id) {
        bookRepository.delete(getBookById(id));
    }
}
