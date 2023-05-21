package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.dto.ContentDTO;
import com.phuongnh.personal.library_management_system.mapper.ContentMapper;
import com.phuongnh.personal.library_management_system.model.Content;
import com.phuongnh.personal.library_management_system.model.ContentType;
import com.phuongnh.personal.library_management_system.model.User;
import com.phuongnh.personal.library_management_system.model.UserRole;
import com.phuongnh.personal.library_management_system.repository.ContentRepository;
import com.phuongnh.personal.library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private UserRepository userRepository;

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public Content getContentById(UUID contentId) {
        return contentRepository.findById(contentId).orElseThrow(() -> new EntityNotFoundException("Content not found with ID: " + contentId));
    }

    public Content createContent(ContentDTO contentDTO) {
        Content content = contentMapper.toEntity(contentDTO);
        UUID authorId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        content.setAuthor(userRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + authorId)));
        content.setDate(LocalDateTime.now());
        return contentRepository.save(content);
    }

    public Content updateContent(UUID contentId, ContentDTO contentDTO) {
        Content existingContent = getContentById(contentId);

        UUID authorId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (
                author.getRole() != UserRole.ADMIN &&
                author.getRole() != UserRole.SUPERUSER &&
                author.getRole() != UserRole.STAFF &&
                authorId != existingContent.getAuthor().getId()
        ) {
            throw new RuntimeException("You are not authorized to update this content");
        }

        existingContent.setContentType(contentDTO.getContentType() != null ? contentDTO.getContentType() : existingContent.getContentType());
        existingContent.setTitle(contentDTO.getTitle() != null ? contentDTO.getTitle() : existingContent.getTitle());
        existingContent.setContent(contentDTO.getContent() != null ? contentDTO.getContent() : existingContent.getContent());
        existingContent.setShowOnHomePage(contentDTO.getShowOnHomePage() != null ? contentDTO.getShowOnHomePage() : existingContent.getShowOnHomePage());
        existingContent.setImgsrc(contentDTO.getImgsrc() != null ? contentDTO.getImgsrc() : existingContent.getImgsrc());
        existingContent.setDate(LocalDateTime.now());
        existingContent.setAuthor(userRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + author.getId())));

        return contentRepository.save(existingContent);
    }

    public void deleteContent(UUID contentId) {
        Content content = getContentById(contentId);
        contentRepository.delete(content);
    }

    public List<Content> getAllContentsByType(ContentType contentType) {
        return contentRepository.findAllByContentType(contentType);
    }

    public List<Content> getHomePageContents() {
        return contentRepository.findAllByShowOnHomePage(true);
    }
}
