package com.phuongnh.personal.library_management_system.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(UUID id, Author author) {
        Author existingAuthor = getAuthorById(id);
        existingAuthor.setGivenName(author.getGivenName());
        existingAuthor.setSurname(author.getSurname());
        existingAuthor.setFullName(author.getFullName());
        existingAuthor.setImgsrc(author.getImgsrc());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(UUID id) {
        authorRepository.delete(getAuthorById(id));
    }
}
