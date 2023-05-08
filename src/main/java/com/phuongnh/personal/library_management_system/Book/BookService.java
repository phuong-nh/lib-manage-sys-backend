package com.phuongnh.personal.library_management_system.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book createBook(Book book) {
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
