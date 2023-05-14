package com.phuongnh.personal.library_management_system.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getAllContents() {
        return contentRepository.findAll();
    }

    public Content getContentById(UUID contentId) {
        return contentRepository.findById(contentId).orElseThrow(() -> new EntityNotFoundException("Content not found with ID: " + contentId));
    }

    public Content createContent(Content content) {
        return contentRepository.save(content);
    }

    public Content updateContent(UUID contentId, Content content) {
        Content existingContent = getContentById(contentId);
        existingContent.setContentType(content.getContentType());
        existingContent.setTitle(content.getTitle());
        existingContent.setContent(content.getContent());
        existingContent.setImgsrc(content.getImgsrc());
        existingContent.setDate(content.getDate());
        existingContent.setShowOnHomePage(content.getShowOnHomePage());
        existingContent.setAuthor(content.getAuthor());
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
