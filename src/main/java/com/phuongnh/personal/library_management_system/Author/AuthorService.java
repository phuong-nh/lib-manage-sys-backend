package com.phuongnh.personal.library_management_system.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorDTO toAuthorDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getGivenName(),
                author.getSurname(),
                author.isSurnameFirst(),
                author.getImgsrc(),
                author.getAuthorBio()
        );
    }

    private Author toAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setGivenName(authorDTO.getGivenName());
        author.setSurname(authorDTO.getSurname());
        author.setSurnameFirst(authorDTO.isSurnameFirst());
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
        Author savedAuthor = authorRepository.save(author);
        return toAuthorDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(UUID id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        existingAuthor.setGivenName(authorDTO.getGivenName());
        existingAuthor.setSurname(authorDTO.getSurname());
        existingAuthor.setSurnameFirst(authorDTO.isSurnameFirst());
        existingAuthor.setImgsrc(authorDTO.getImgsrc());
        Author updatedAuthor = authorRepository.save(existingAuthor);
        return toAuthorDTO(updatedAuthor);
    }

    public void deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.delete(author);
    }
}
