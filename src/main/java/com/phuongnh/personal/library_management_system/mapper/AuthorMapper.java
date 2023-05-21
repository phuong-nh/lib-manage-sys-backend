package com.phuongnh.personal.library_management_system.mapper;

import com.phuongnh.personal.library_management_system.dto.AuthorDTO;
import com.phuongnh.personal.library_management_system.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public static AuthorDTO toAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getGivenName(),
                author.getSurName(),
                author.getIsGivenSurName(),
                author.getImgsrc(),
                ContentMapper.toDTO(author.getAuthorBio())
        );
    }

    public static Author toAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setGivenName(authorDTO.getGivenName());
        author.setSurName(authorDTO.getSurName());
        author.setIsGivenSurName(authorDTO.getIsGivenSurName());
        author.setImgsrc(authorDTO.getImgsrc());
        author.setAuthorBio(ContentMapper.toEntity(authorDTO.getAuthorBio()));
        return author;
    }
}
