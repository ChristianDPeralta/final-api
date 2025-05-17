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
    private UserRepository userRepository;

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
        @RequestParam Long userId,
        @RequestBody Comment comment
    ) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        if (user == null || post == null) return null;
        comment.setUser(user);
        comment.setPost(post);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
