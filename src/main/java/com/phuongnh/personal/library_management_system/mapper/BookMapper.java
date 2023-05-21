package com.phuongnh.personal.library_management_system.mapper;

import com.phuongnh.personal.library_management_system.dto.BookDTO;
import com.phuongnh.personal.library_management_system.model.Author;
import com.phuongnh.personal.library_management_system.model.Book;
import com.phuongnh.personal.library_management_system.model.BookCopy;
import com.phuongnh.personal.library_management_system.model.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    public static BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setBookBio(book.getBookBio());
        dto.setPublisher(book.getPublisher());
        dto.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
        dto.setCategoryIds(book.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        dto.setBookCopyIds(book.getCopies().stream().map(BookCopy::getId).collect(Collectors.toList()));
        dto.setPublishedDate(book.getPublishedDate());
        dto.setNumberOfCopies(book.getCopies().size());
        dto.setImgsrc(book.getImgsrc());
        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setBookBio(dto.getBookBio());
        book.setPublisher(dto.getPublisher());
        book.setPublishedDate(dto.getPublishedDate());
        book.setImgsrc(dto.getImgsrc());
        return book;
    }
}
