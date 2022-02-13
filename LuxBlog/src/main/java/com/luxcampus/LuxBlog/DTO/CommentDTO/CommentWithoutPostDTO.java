package com.luxcampus.LuxBlog.DTO.CommentDTO;

import com.luxcampus.LuxBlog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CommentWithoutPostDTO {
    private final Long id;
    private final String text;
    private final Timestamp creationDate;

   public CommentWithoutPostDTO(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.creationDate = comment.getCreationDate();
    }
}
