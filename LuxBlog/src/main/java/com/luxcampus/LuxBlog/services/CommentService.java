package com.luxcampus.LuxBlog.services;

import com.luxcampus.LuxBlog.entity.Comment;
import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostsService postsService;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostsService postsService) {
        this.commentRepository = commentRepository;
        this.postsService = postsService;
    }

    public List<Comment> getCommentsFromPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment getById(Long commentId) {
        return commentRepository.getById(commentId);
    }

    public void addNewCommentToPost(Long id, Comment comment) {
        Post post = postsService.findById(id);
        List<Comment> commentList = post.getComments();
        commentList.add(comment);
        comment.setPost(post);
        commentRepository.save(comment);
    }
}
