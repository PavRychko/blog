package com.luxcampus.LuxBlog.DTO.PostDTO;

import com.luxcampus.LuxBlog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PostWithoutCommentsDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final boolean star;

    public PostWithoutCommentsDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.star = post.isStar();

    }
}
