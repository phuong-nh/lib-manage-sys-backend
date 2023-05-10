package com.phuongnh.personal.library_management_system.Book;

import com.phuongnh.personal.library_management_system.Author.Author;
import com.phuongnh.personal.library_management_system.Category.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setBookBio(book.getBookBio());
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
        book.setBookBio(dto.getBookBio());
        book.setPublisher(dto.getPublisher());
        book.setPublishedDate(dto.getPublishedDate());
        book.setImgsrc(dto.getImgsrc());
        return book;
    }

    public Book updateEntityFromDTO(BookDTO bookDTO, Book existingBook) {
        if (bookDTO.getIsbn() != null) {
            existingBook.setIsbn(bookDTO.getIsbn());
        }
        if (bookDTO.getTitle() != null) {
            existingBook.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getDescription() != null) {
            existingBook.setDescription(bookDTO.getDescription());
        }
        if (bookDTO.getPublisher() != null) {
            existingBook.setPublisher(bookDTO.getPublisher());
        }
        if (bookDTO.getPublishedDate() != null) {
            existingBook.setPublishedDate(bookDTO.getPublishedDate());
        }
        if (bookDTO.getImgsrc() != null) {
            existingBook.setImgsrc(bookDTO.getImgsrc());
        }
        return existingBook;
    }
}
