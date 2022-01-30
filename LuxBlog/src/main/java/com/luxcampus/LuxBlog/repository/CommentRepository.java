package com.luxcampus.LuxBlog.repository;

import com.luxcampus.LuxBlog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);

}
