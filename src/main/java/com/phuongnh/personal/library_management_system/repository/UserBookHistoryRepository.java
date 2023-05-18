package com.phuongnh.personal.library_management_system.repository;

import com.phuongnh.personal.library_management_system.model.UserBookHistory;
import com.phuongnh.personal.library_management_system.model.UserBookHistoryID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface UserBookHistoryRepository extends JpaRepository<UserBookHistory, UserBookHistoryID> {
    List<UserBookHistory> findByUserId(UUID userId);
}
