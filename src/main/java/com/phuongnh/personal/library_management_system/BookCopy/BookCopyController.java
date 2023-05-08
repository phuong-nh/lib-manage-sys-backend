package com.phuongnh.personal.library_management_system.BookCopy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/copies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping
    public ResponseEntity<List<BookCopy>> getAllBookCopies() {
        return ResponseEntity.ok(bookCopyService.getAllBookCopies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopy> getBookCopyById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCopyService.getBookCopyById(id));
    }

    @PostMapping
    public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopy bookCopy) {
        return new ResponseEntity<>(bookCopyService.createBookCopy(bookCopy), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCopy> updateBookCopy(@PathVariable UUID id, @RequestBody BookCopy bookCopy) {
        return ResponseEntity.ok(bookCopyService.updateBookCopy(id, bookCopy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable UUID id) {
        bookCopyService.deleteBookCopy(id);
        return ResponseEntity.noContent().build();
    }
}

