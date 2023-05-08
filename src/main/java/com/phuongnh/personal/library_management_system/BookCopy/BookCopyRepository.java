package com.phuongnh.personal.library_management_system.BookCopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {
}
