package com.phuongnh.personal.library_management_system.Content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
}
