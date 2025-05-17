package com.peralta.socialmedia;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // <-- FIX: Use "users" instead of "user"
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String displayName;
    private String avatarUrl;

    // Getters and setters...
}
