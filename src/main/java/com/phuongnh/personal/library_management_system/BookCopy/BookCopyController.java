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
    public ResponseEntity<List<BookCopyDTO>> getAllBookCopies() {
        return ResponseEntity.ok(bookCopyService.getAllBookCopies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDTO> getBookCopyById(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCopyService.getBookCopyById(id));
    }

    @PostMapping
    public ResponseEntity<BookCopyDTO> createBookCopy(@RequestBody BookCopyDTO bookCopyDTO) {
        return new ResponseEntity<>(bookCopyService.createBookCopy(bookCopyDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookCopyDTO> updateBookCopy(@PathVariable UUID id, @RequestBody BookCopyDTO bookCopyDTO) {
        return ResponseEntity.ok(bookCopyService.updateBookCopy(id, bookCopyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable UUID id) {
        bookCopyService.deleteBookCopy(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/borrow/{borrowerId}")
    public ResponseEntity<BookCopyDTO> borrowBook(@PathVariable UUID id, @PathVariable UUID borrowerId) {
        return ResponseEntity.ok(bookCopyService.borrowBook(id, borrowerId));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BookCopyDTO> returnBook(@PathVariable UUID id) {
        return ResponseEntity.ok(bookCopyService.returnBook(id));
    }
}

