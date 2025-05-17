package com.peralta.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsForPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return Collections.emptyList();
        return commentRepository.findByPost(post);
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment createCommentForPost(
        @PathVariable Long postId,
        @RequestBody Comment comment
    ) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return null;

        // Set the author to "Anonymous" if not provided
        if (comment.getAuthor() == null || comment.getAuthor().trim().isEmpty()) {
            comment.setAuthor("Anonymous");
        }

        comment.setPost(post);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
