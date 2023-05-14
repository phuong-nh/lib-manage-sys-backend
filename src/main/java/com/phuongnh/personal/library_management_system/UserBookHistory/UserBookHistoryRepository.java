package com.phuongnh.personal.library_management_system.UserBookHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface UserBookHistoryRepository extends JpaRepository<UserBookHistory, UserBookHistoryID> {
    List<UserBookHistory> findByUserId(UUID userId);
}
