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

    @Column(name = "given_name")
    private String givenName;

    @Column(name = "sur_name")
    private String surName;

    @Column(name = "is_given_surname", nullable = false)
    private Boolean isGivenSurname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role = UserRole.USER;

    @Column(name = "imgsrc")
    private String imgsrc;

    @Column(name = "is_banned", nullable = false)
    private Boolean isBanned = false;
}

