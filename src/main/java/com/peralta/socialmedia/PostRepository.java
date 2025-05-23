package com.peralta.socialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll(Sort sort);
}
