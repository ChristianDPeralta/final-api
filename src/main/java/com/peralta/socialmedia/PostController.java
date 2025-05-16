package com.peralta.socialmedia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // Adjust this in production
public class PostController {

    @Autowired
    private PostRepository repository;

    @GetMapping
    public List<Post> getAllPosts() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @PostMapping
    public Post createPost(@Valid @RequestBody Post post) {
        post.setTimestamp(LocalDateTime.now());
        return repository.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @Valid @RequestBody Post updatedPost) {
        return repository.findById(id).map(existing -> {
            existing.setContent(updatedPost.getContent());
            existing.setImageUrl(updatedPost.getImageUrl());
            return repository.save(existing);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
