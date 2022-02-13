package com.luxcampus.LuxBlog.DTO.PostDTO;

import com.luxcampus.LuxBlog.DTO.CommentDTO.CommentWithoutPostDTO;
import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.mappers.CommentMapper;
import lombok.Data;

import java.util.List;

@Data
public class PostWithCommentDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final boolean star;
    private final List<CommentWithoutPostDTO> comments;

    public PostWithCommentDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.star = post.isStar();
        CommentMapper commentMapper = new CommentMapper();
        this.comments = commentMapper.map(post.getComments());
    }
}