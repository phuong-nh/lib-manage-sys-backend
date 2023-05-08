package com.phuongnh.personal.library_management_system.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.Set;

import com.phuongnh.personal.library_management_system.Author.Author;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopy;
import com.phuongnh.personal.library_management_system.Category.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "isbn", unique = true, nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookCopy> copies;

    @Column(name = "imgsrc")
    private String imgsrc;
}

