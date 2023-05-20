package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.ContentDTO;
import com.phuongnh.personal.library_management_system.mapper.ContentMapper;
import com.phuongnh.personal.library_management_system.model.Content;
import com.phuongnh.personal.library_management_system.model.ContentType;
import com.phuongnh.personal.library_management_system.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/contents")
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
    public ResponseEntity<ContentDTO> getContentById(@PathVariable UUID contentId) {
        Content content = contentService.getContentById(contentId);
        ContentDTO contentDTO = contentMapper.toDTO(content);
        return new ResponseEntity<>(contentDTO, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF', 'SUPERUSER')")
    public ResponseEntity<ContentDTO> createContent(@RequestBody ContentDTO contentDTO) {
        Content createdContent = contentService.createContent(contentDTO);
        return new ResponseEntity<>(contentMapper.toDTO(createdContent), HttpStatus.CREATED);
    }

    @PutMapping("/{contentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF', 'SUPERUSER')")
    public ResponseEntity<ContentDTO> updateContent(@PathVariable UUID contentId, @RequestBody ContentDTO contentDTO) {
        Content updatedContent = contentService.updateContent(contentId, contentDTO);
        return new ResponseEntity<>(contentMapper.toDTO(updatedContent), HttpStatus.OK);
    }

    @DeleteMapping("/{contentId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'STAFF', 'SUPERUSER')")
    public ResponseEntity<Void> deleteContent(@PathVariable UUID contentId) {
        contentService.deleteContent(contentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
