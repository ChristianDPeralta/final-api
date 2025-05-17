package com.peralta.socialmedia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // For dev only; restrict in prod
public class PostController {

    @Autowired
    private PostRepository repository;

    @GetMapping
    public List<Post> getAllPosts() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @PostMapping
    public Post createPost(@Valid @RequestBody Post post) {
        // If author is missing or blank, set as "Anonymous"
        if (post.getAuthor() == null || post.getAuthor().trim().isEmpty()) {
            post.setAuthor("Anonymous");
        }
        post.setTimestamp(LocalDateTime.now());
        return repository.save(post);
    }

    // BULK CREATE ENDPOINT
    @PostMapping("/bulk")
    public List<Post> createPostsBulk(@Valid @RequestBody List<Post> posts) {
        List<Post> result = new ArrayList<>();
        for (Post post : posts) {
            if (post.getAuthor() == null || post.getAuthor().trim().isEmpty()) {
                post.setAuthor("Anonymous");
            }
            post.setTimestamp(LocalDateTime.now());
            result.add(post);
        }
        return repository.saveAll(result);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @Valid @RequestBody Post updatedPost) {
        return repository.findById(id).map(existing -> {
            existing.setContent(updatedPost.getContent());
            existing.setImageUrl(updatedPost.getImageUrl());
            // Optionally update author, but keep anonymous logic
            if (updatedPost.getAuthor() == null || updatedPost.getAuthor().trim().isEmpty()) {
                existing.setAuthor("Anonymous");
            } else {
                existing.setAuthor(updatedPost.getAuthor());
            }
            return repository.save(existing);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
