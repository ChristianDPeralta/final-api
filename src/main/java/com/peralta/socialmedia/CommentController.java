package com.peralta.socialmedia;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*") // For dev only; restrict in prod
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private PostRepository postRepository;

    // Get all comments for a specific post
    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            return commentRepository.findByPost(post);
        }
        return List.of(); // Return empty list if post not found
    }

    // Create a new comment
    @PostMapping
    public Comment createComment(@Valid @RequestBody Comment comment) {
        if (comment.getPost() != null && comment.getPost().getId() != null) {
            Post post = postRepository.findById(comment.getPost().getId()).orElse(null);
            comment.setPost(post);
        }
        comment.setTimestamp(LocalDateTime.now());
        // Do NOT overwrite or ignore author; it's set from frontend
        return commentRepository.save(comment);
    }

    // Update a comment
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @Valid @RequestBody Comment updatedComment) {
        return commentRepository.findById(id).map(existing -> {
            existing.setContent(updatedComment.getContent());
            return commentRepository.save(existing);
        }).orElse(null);
    }

    // Delete a comment
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
