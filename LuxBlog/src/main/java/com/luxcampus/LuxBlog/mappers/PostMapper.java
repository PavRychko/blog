package com.luxcampus.LuxBlog.mappers;


import com.luxcampus.LuxBlog.DTO.PostDTO.PostWithCommentDTO;
import com.luxcampus.LuxBlog.entity.Post;

public class PostMapper {

    public PostWithCommentDTO map(Post post){
       return new PostWithCommentDTO(post);
    }
}
