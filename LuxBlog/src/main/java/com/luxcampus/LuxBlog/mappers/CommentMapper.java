package com.luxcampus.LuxBlog.mappers;

import com.luxcampus.LuxBlog.DTO.CommentDTO.CommentWithoutPostDTO;
import com.luxcampus.LuxBlog.entity.Comment;


import java.util.List;
import java.util.stream.Collectors;

public class CommentMapper {

    public List<CommentWithoutPostDTO> map(List<Comment> comments) {
        return comments.stream()
                .map(CommentWithoutPostDTO::new).collect(Collectors.toList());

    }
}
