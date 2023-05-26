package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.BookCopyDTO;
import com.phuongnh.personal.library_management_system.service.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/copies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping
    public ResponseEntity<List<BookCopyDTO>> getAllBookCopies() {
        return ResponseEntity.ok(bookCopyService.getAllBookCopies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDTO> getBookCopyById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCopyService.getBookCopyById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<BookCopyDTO> createBookCopy(@RequestBody BookCopyDTO bookCopyDTO) {
        return new ResponseEntity<>(bookCopyService.createBookCopy(bookCopyDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<BookCopyDTO> updateBookCopy(@PathVariable UUID id, @RequestBody BookCopyDTO bookCopyDTO) {
        return ResponseEntity.ok(bookCopyService.updateBookCopy(id, bookCopyDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERUSER')")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable UUID id) {
        bookCopyService.deleteBookCopy(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/borrow")
    @PreAuthorize("hasAnyAuthority('USER','STAFF','ADMIN','SUPERUSER')")
    public ResponseEntity<BookCopyDTO> borrowBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCopyService.borrowBook(id));
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasAnyAuthority('USER','STAFF','ADMIN','SUPERUSER')")
    public ResponseEntity<BookCopyDTO> returnBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCopyService.returnBook(id));
    }
}

