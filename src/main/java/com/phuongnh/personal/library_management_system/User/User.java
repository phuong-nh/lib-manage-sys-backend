package com.phuongnh.personal.library_management_system.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "library_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "givenname")
    private String givenName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "is_given_surname", nullable = false)
    private boolean isGivenSurname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "imgsrc")
    private String imgsrc;

    @Column(name = "is_banned", nullable = false)
    private boolean isBanned;
}

enum UserRole {
    USER,
    STAFF,
    ADMIN,
    SUPERUSER
}

