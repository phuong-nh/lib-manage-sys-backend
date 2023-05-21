package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.dto.AuthorDTO;
import com.phuongnh.personal.library_management_system.dto.ContentDTO;
import com.phuongnh.personal.library_management_system.exception.AuthorNotFoundException;
import com.phuongnh.personal.library_management_system.mapper.AuthorMapper;
import com.phuongnh.personal.library_management_system.mapper.ContentMapper;
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
    @Autowired
    private ContentService contentService;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id.toString()));
        return AuthorMapper.toAuthorDTO(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.toAuthor(authorDTO);

        if (authorDTO.getAuthorBio() != null) {
            ContentDTO authorBio = authorDTO.getAuthorBio();
            authorBio.setContentType(ContentType.AUTHOR_BIO);
            author.setAuthorBio(contentService.createContent(authorBio));
        } else {
            ContentDTO defaultAuthorBio = new ContentDTO();
            defaultAuthorBio.setContentType(ContentType.AUTHOR_BIO);
            defaultAuthorBio.setTitle("Author Bio");
            defaultAuthorBio.setContent("This is the default author bio");
            author.setAuthorBio(contentService.createContent(defaultAuthorBio));
        }

        Author savedAuthor = authorRepository.save(author);
        return AuthorMapper.toAuthorDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(UUID id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id.toString()));

        if (authorDTO.getAuthorBio() != null) {
            Content authorBio = existingAuthor.getAuthorBio();
            authorBio.setTitle(authorDTO.getAuthorBio().getTitle());
            authorBio.setContent(authorDTO.getAuthorBio().getContent());
            contentRepository.save(authorBio);
        }

        existingAuthor.setGivenName(authorDTO.getGivenName() == null ? existingAuthor.getGivenName() : authorDTO.getGivenName());
        existingAuthor.setSurName(authorDTO.getSurName() == null ? existingAuthor.getSurName() : authorDTO.getSurName());
        existingAuthor.setIsGivenSurName(authorDTO.getIsGivenSurName() == null ? existingAuthor.getIsGivenSurName() : authorDTO.getIsGivenSurName());
        existingAuthor.setImgsrc(authorDTO.getImgsrc() == null ? existingAuthor.getImgsrc() : authorDTO.getImgsrc());

        if (authorDTO.getAuthorBio() != null) {
            contentService.updateContent(authorDTO.getId(), authorDTO.getAuthorBio());
        }

        Author savedAuthor = authorRepository.save(existingAuthor);
        return AuthorMapper.toAuthorDTO(savedAuthor);
    }

    public AuthorDTO deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id.toString()));
        authorRepository.delete(author);
        return AuthorMapper.toAuthorDTO(author);
    }
}
