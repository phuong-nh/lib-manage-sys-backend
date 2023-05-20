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
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/{id}/copies-status")
    public ResponseEntity<List<BookCopyStatus>> getCopiesStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getCopiesStatus(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookDTO));
    }

    @PostMapping("/{id}/add-copies")
    @PreAuthorize("hasAnyAuthority('STAFF', 'ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> addCopies(@PathVariable UUID id, @RequestParam("amount") int amount) {
        return ResponseEntity.ok(bookService.addCopies(id, amount));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERUSER')")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
