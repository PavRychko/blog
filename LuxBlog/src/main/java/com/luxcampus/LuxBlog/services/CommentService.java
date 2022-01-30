package com.luxcampus.LuxBlog.services;

import com.luxcampus.LuxBlog.entity.Comment;
import com.luxcampus.LuxBlog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService (CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> getCommentsFromPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
