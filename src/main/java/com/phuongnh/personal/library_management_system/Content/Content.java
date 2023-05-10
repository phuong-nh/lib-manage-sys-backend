package com.phuongnh.personal.library_management_system.Content;

import com.phuongnh.personal.library_management_system.Author.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.phuongnh.personal.library_management_system.User.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "show_on_home_page")
    private boolean showOnHomePage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Author authorBio;
}

enum ContentType {
    NEWS,
    BLOG,
    AUTHOR_BIO,
    BOOK_BIO,
    OTHER
}
