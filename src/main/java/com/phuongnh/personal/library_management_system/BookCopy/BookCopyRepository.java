package com.phuongnh.personal.library_management_system.BookCopy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phuongnh.personal.library_management_system.User.User;

import java.util.UUID;
import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, UUID> {
    List<BookCopy> findAllByBorrower(User borrower);
}
