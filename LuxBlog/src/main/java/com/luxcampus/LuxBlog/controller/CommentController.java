package com.luxcampus.LuxBlog.controller;

import com.luxcampus.LuxBlog.entity.Comment;
import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.services.CommentService;
import com.luxcampus.LuxBlog.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CommentController {
    private final CommentService commentService;
    private final PostsService postsService;

    @Autowired
    public CommentController(CommentService commentService, PostsService postsService) {
        this.postsService = postsService;
        this.commentService = commentService;
    }

    @GetMapping(path = "api/v1/posts/{id}/comments")
    public List<Comment> getComments(@PathVariable("id") Long postId) {
        return commentService.getCommentsFromPost(postId);
    }

    @GetMapping(path = "api/v1/comments/{id}")
    public Comment getCommentById( @PathVariable Long id) {
        return commentService.getById(id);
    }

    @PostMapping(path ="api/v1/posts/{id}/comments")
    public void addNewCommentToPost(@PathVariable Long id, @RequestBody Comment comment){
        commentService.addNewCommentToPost(id, comment);
    }
}
