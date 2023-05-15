package com.phuongnh.personal.library_management_system.Author;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.phuongnh.personal.library_management_system.Book.Book;
import com.phuongnh.personal.library_management_system.Content.Content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "given_name", nullable = false)
    private String givenName;

    @Column(name = "sur_name", nullable = false)
    private String surName;

    @Column(name = "is_given_surname", nullable = false)
    private Boolean isGivenSurname;

    @Column(name = "imgsrc")
    private String imgsrc;

    @OneToOne(mappedBy = "authorBio", fetch = FetchType.LAZY)
    private Content authorBio;

    @Column(name = "author_bio")
    private UUID authorBioId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;
}
