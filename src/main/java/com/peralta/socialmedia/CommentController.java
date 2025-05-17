package com.peralta.socialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // Create a comment
    @PostMapping
    public Comment createComment(@RequestParam Long userId, @RequestParam Long postId, @RequestBody Comment comment) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);
        if (user == null || post == null) return null;
        comment.setUser(user);
        comment.setPost(post);
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }
}
