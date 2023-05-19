package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.BookDTO;
import com.phuongnh.personal.library_management_system.model.BookCopyStatus;
import com.phuongnh.personal.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        try {
            return ResponseEntity.ok(bookService.getAllBooks());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (Exception e) {
            if (e.getMessage().equals("BookNotFoundException")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping("/{id}/copies-status")
    public ResponseEntity<List<BookCopyStatus>> getCopiesStatus(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(bookService.getCopiesStatus(id));
        } catch (Exception e) {
            if (e.getMessage().equals("BookNotFoundException")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        try {
            return new ResponseEntity<>(bookService.createBook(bookDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            if (e.getMessage().equals("BookNotFoundException")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PostMapping("/{id}/add-copies")
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> addCopies(@PathVariable UUID id, @RequestParam("amount") int amount) {
        try {
            return ResponseEntity.ok(bookService.addCopies(id, amount));
        } catch (Exception e) {
            if (e.getMessage().equals("BookNotFoundException")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDTO) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(bookService.deleteBook(id));
        } catch (Exception e) {
            if (e.getMessage().equals("BookNotFoundException")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}
