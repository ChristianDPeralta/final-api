package com.peralta.socialmedia;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String author; // <--- Add this field

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // ... any other fields, constructors, etc.

    // --- GETTERS AND SETTERS ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() { // <--- Add this getter
        return author;
    }

    public void setAuthor(String author) { // <--- Add this setter
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    // Add any other getters/setters if you have more fields
}
