package com.luxcampus.LuxBlog.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.LuxBlog.DTO.CommentDTO.CommentWithoutPostDTO;
import com.luxcampus.LuxBlog.DTO.PostDTO.PostWithCommentDTO;
import com.luxcampus.LuxBlog.entity.Comment;
import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.CommentRepository;
import com.luxcampus.LuxBlog.repository.PostRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CommentServiceTest {
    @Autowired
    private MockMvc mockmvc;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private final String url = "http://localhost:8080/api/v1/posts/{id}/comments";
    private final Post post1 = Post.builder()
            .id(1L)
            .title("something important")
            .content("nothing special")
            .build();
    private final Post post2 = Post.builder()
            .id(2L)
            .title("nothing important")
            .content("something special")
            .build();
    private final Comment comment1 = Comment.builder()
            .id(1L)
            .text("super comment")
            .creationDate(Timestamp.valueOf("2019-04-19 12:00:17"))
            .post(post1)
            .build();
    private final Comment comment2 = Comment.builder()
            .id(2L)
            .text("puper comment")
            .creationDate(Timestamp.valueOf("2021-04-21 12:00:17"))
            .post(post1)
            .build();
    private final List<Comment> expectedCommentsInPost1 = List.of(comment1, comment2);
    private final List<Comment> expectedCommentsInPost2 = Collections.emptyList();


    @Test
    void getCommentsFromPost() throws Exception {
        when(commentRepository.findByPostId(1L)).thenReturn(expectedCommentsInPost1);
        when(commentRepository.findByPostId(2L)).thenReturn(expectedCommentsInPost2);

        mockmvc.perform(get(url, post1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCommentsInPost1)));
        mockmvc.perform(get(url, post2.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedCommentsInPost2)));
    }


    @Test
    void getCommentById() throws Exception {
        String urlToFindCommentById = "http://localhost:8080/api/v1/comments/{id}";

        when(commentRepository.getById(1L)).thenReturn(comment1);
        when(commentRepository.getById(2L)).thenReturn(comment2);

        mockmvc.perform(get(urlToFindCommentById, comment1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comment1)));
        mockmvc.perform(get(urlToFindCommentById, comment2.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comment2)));

    }



    @Test
    void addNewCommentToPost() throws Exception {
        comment1.setPost(null);
        comment2.setPost(null);
        assertNull(post1.getComments());
        List<Comment> commentList = new ArrayList<>();
        post1.setComments(commentList);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post1));

        mockmvc.perform(post(url, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment1)))
                .andExpect(status().isOk());
        mockmvc.perform(post(url, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment2)))
                .andExpect(status().isOk());
        PostWithCommentDTO post1DTO = new PostWithCommentDTO(post1);
        CommentWithoutPostDTO comment1DTO = new CommentWithoutPostDTO(comment1);
        CommentWithoutPostDTO comment2DTO = new CommentWithoutPostDTO(comment2);

        assertNotNull(post1DTO.getComments());
        assertEquals(comment1DTO, post1DTO.getComments().get(0));
        assertEquals(comment2DTO, post1DTO.getComments().get(1));

    }
}