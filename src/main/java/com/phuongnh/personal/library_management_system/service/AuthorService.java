package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.dto.AuthorDTO;
import com.phuongnh.personal.library_management_system.model.Author;
import com.phuongnh.personal.library_management_system.model.Content;
import com.phuongnh.personal.library_management_system.repository.AuthorRepository;
import com.phuongnh.personal.library_management_system.repository.ContentRepository;
import com.phuongnh.personal.library_management_system.model.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ContentRepository contentRepository;

    private AuthorDTO toAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getGivenName(),
                author.getSurName(),
                author.getIsGivenSurName(),
                author.getImgsrc(),
                author.getAuthorBio()
        );
    }

    private Author toAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setGivenName(authorDTO.getGivenName());
        author.setSurName(authorDTO.getSurName());
        author.setIsGivenSurName(authorDTO.getIsGivenSurName());
        author.setImgsrc(authorDTO.getImgsrc());
        author.setAuthorBio(authorDTO.getAuthorBio());
        return author;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toAuthorDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        return toAuthorDTO(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = toAuthor(authorDTO);

        if (authorDTO.getAuthorBio() != null) {
            Content authorBio = authorDTO.getAuthorBio();
            authorBio.setContentType(ContentType.AUTHOR_BIO);
            author.setAuthorBio(contentRepository.save(authorBio));
        } else {
            Content defaultAuthorBio = new Content();
            defaultAuthorBio.setContentType(ContentType.AUTHOR_BIO);
            defaultAuthorBio.setTitle("Author Bio");
            defaultAuthorBio.setContent("This is the default author bio");
            author.setAuthorBio(contentRepository.save(defaultAuthorBio));
        }

        Author savedAuthor = authorRepository.save(author);
        return toAuthorDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(UUID id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));

        if (authorDTO.getAuthorBio() != null) {
            Content authorBio = existingAuthor.getAuthorBio();
            authorBio.setTitle(authorDTO.getAuthorBio().getTitle());
            authorBio.setContent(authorDTO.getAuthorBio().getContent());
            contentRepository.save(authorBio);
        }

        if (authorDTO.getGivenName() != null) {
            existingAuthor.setGivenName(authorDTO.getGivenName());
        }

        if (authorDTO.getSurName() != null) {
            existingAuthor.setSurName(authorDTO.getSurName());
        }

        if (authorDTO.getIsGivenSurName() != null) {
            existingAuthor.setIsGivenSurName(authorDTO.getIsGivenSurName());
        }

        if (authorDTO.getImgsrc() != null) {
            existingAuthor.setImgsrc(authorDTO.getImgsrc());
        }

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return toAuthorDTO(updatedAuthor);
    }

    public void deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
    }
}
