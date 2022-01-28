package com.luxcampus.LuxBlog.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PostsServiceTest {
    @Autowired
    private MockMvc mockmvc;
    @MockBean
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;
    String url = "http://localhost:8080/api/v1/posts";
    String urlForDeleteAndPut = url.concat("/{id}");

    @Test
    void getAllPostsByUrlTest() {
        Post post1 = Post.builder()
                .id(1L)
                .title("something important")
                .content("nothing special")
                .build();
        Post post2 = Post.builder()
                .id(2L)
                .title("something not important")
                .content("some special information")
                .build();
        List<Post> expectedPosts = List.of(post1, post2);

        when(postRepository.findAll()).thenReturn(expectedPosts);

        try {
            mockmvc.perform(get(url))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(expectedPosts)));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void addNewPostTest() {
        PostsService postService = new PostsService(postRepository);
        Post post = Post.builder()
                .id(1L)
                .title("something important")
                .content("nothing special")
                .build();
        postService.addNewPost(post);
        verify(postRepository, times(1)).save(post);

    }


    @DisplayName("when delete post get status code 200 OK, and deleted post as Json")
    @Test
    void deletePostTest() throws Exception {
        Post expectedPost = Post.builder()
                .id(1L)
                .title("something important")
                .content("nothing special")
                .build();

        when(postRepository.existsById(1L)).thenReturn(true);
        when(postRepository.findById(1L)).thenReturn(Optional.of(expectedPost));

        mockmvc.perform(delete(urlForDeleteAndPut, expectedPost.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPost)));

    }

    @Test
    void updatePost() throws Exception {
        Post expectedPost = Post.builder()
                .id(1L)
                .title("something important")
                .content("nothing special")
                .build();
        Post updatedPost = Post.builder()
                .id(1L)
                .title("nothing important")
                .content("nothing special")
                .build();

        when(postRepository.existsById(1L)).thenReturn(true);
        when(postRepository.findById(1L)).thenReturn(Optional.of(expectedPost));

        mockmvc.perform(put(urlForDeleteAndPut, expectedPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPost)));
    }


}