package com.phuongnh.personal.library_management_system.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private ContentMapper contentMapper;

    @GetMapping
    public ResponseEntity<List<Content>> getAllContents() {
        List<Content> contents = contentService.getAllContents();
        return new ResponseEntity<>(contents, HttpStatus.OK);
    }

    @GetMapping("/type/{contentType}")
    public ResponseEntity<List<ContentDTO>> getContentsByType(@PathVariable ContentType contentType) {
        List<Content> contents = contentService.getAllContentsByType(contentType);
        List<ContentDTO> contentDTOs = contents.stream()
                .map(contentMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(contentDTOs, HttpStatus.OK);
    }

    @GetMapping("/homepage")
    public ResponseEntity<List<ContentDTO>> getHomePageContents() {
        List<Content> contents = contentService.getHomePageContents();
        List<ContentDTO> contentDTOs = contents.stream()
                .map(contentMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(contentDTOs, HttpStatus.OK);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<Content> getContentById(@PathVariable UUID contentId) {
        Content content = contentService.getContentById(contentId);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Content> createContent(@RequestBody Content content) {
        Content createdContent = contentService.createContent(content);
        return new ResponseEntity<>(createdContent, HttpStatus.CREATED);
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<Content> updateContent(@PathVariable UUID contentId, @RequestBody Content content) {
        Content updatedContent = contentService.updateContent(contentId, content);
        return new ResponseEntity<>(updatedContent, HttpStatus.OK);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> deleteContent(@PathVariable UUID contentId) {
        contentService.deleteContent(contentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
