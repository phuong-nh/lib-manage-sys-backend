package com.phuongnh.personal.library_management_system.Content;

import org.springframework.stereotype.Component;

@Component
public class ContentMapper {
    public ContentDTO toDTO(Content content) {
        return new ContentDTO(
                content.getContentType(),
                content.getTitle(),
                content.getContent(),
                content.getImgsrc(),
                content.getShowOnHomePage(),
                content.getDate()
        );
    }

    public Content toEntity(ContentDTO contentDTO) {
        Content content = new Content();
        content.setContentType(contentDTO.getContentType());
        content.setTitle(contentDTO.getTitle());
        content.setContent(contentDTO.getContent());
        content.setImgsrc(contentDTO.getImgsrc());
        content.setShowOnHomePage(contentDTO.getShowOnHomePage());
        content.setDate(contentDTO.getDate());
        return content;
    }
}
