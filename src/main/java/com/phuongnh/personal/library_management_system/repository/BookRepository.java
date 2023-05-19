package com.phuongnh.personal.library_management_system.repository;

import com.phuongnh.personal.library_management_system.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(String isbn);
}
