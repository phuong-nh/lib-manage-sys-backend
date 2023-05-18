package com.phuongnh.personal.library_management_system.repository;

import com.phuongnh.personal.library_management_system.model.Content;
import com.phuongnh.personal.library_management_system.model.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
    List<Content> findAllByContentType(ContentType contentType);
    List<Content> findAllByShowOnHomePage(boolean showOnHomePage);
}
