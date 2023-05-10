package com.phuongnh.personal.library_management_system.Book;

import com.phuongnh.personal.library_management_system.Author.Author;
import com.phuongnh.personal.library_management_system.Category.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPublisher(book.getPublisher());
        dto.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
        dto.setCategoryIds(book.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        dto.setPublishedDate(book.getPublishedDate());
        dto.setNumberOfCopies(book.getCopies().size());
        dto.setImgsrc(book.getImgsrc());
        return dto;
    }

    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPublisher(dto.getPublisher());
        book.setPublishedDate(dto.getPublishedDate());
        book.setImgsrc(dto.getImgsrc());
        return book;
    }

    public Book updateEntityFromDTO(BookDTO bookDTO, Book existingBook) {
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setPublisher(bookDTO.getPublisher());
        existingBook.setPublishedDate(bookDTO.getPublishedDate());
        existingBook.setImgsrc(bookDTO.getImgsrc());
        return existingBook;
    }
}
