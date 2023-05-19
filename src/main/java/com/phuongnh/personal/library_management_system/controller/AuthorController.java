package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.AuthorDTO;
import com.phuongnh.personal.library_management_system.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        try {
            return ResponseEntity.ok(authorService.getAllAuthors());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(authorService.getAuthorById(id));
        } catch (Exception e) {
            if (e.getMessage().equals("AuthorNotFoundException")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        try {
            return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().equals("AuthorConflictException")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDTO) {
        try {
            return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
        } catch (Exception e) {
            if (e.getMessage().equals("AuthorNotFoundException")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().equals("AuthorConflictException")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(authorService.deleteAuthor(id));
        } catch (Exception e) {
            if (e.getMessage().equals("AuthorNotFoundException")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
