package com.luxcampus.LuxBlog.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostsServiceTest {
    @Autowired
    private MockMvc mockmvc;
    @MockBean
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private final String url = "http://localhost:8080/api/v1/posts";
    private final String urlForDeleteAndPut = url.concat("/{id}");
    private final Post post = Post.builder()
            .id(1L)
            .title("something important")
            .content("nothing special")
            .build();
    private final Post post2 = Post.builder()
            .id(2L)
            .title("nothing important")
            .content("something special")
            .build();
    private final List<Post> expectedPosts = List.of(post, post2);

    @Test
    void getAllPostsByUrlTest() throws Exception {
        when(postRepository.findAll()).thenReturn(expectedPosts);

        mockmvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPosts)));


    }


    @Test
    void addNewPostTest() {
        PostsService postService = new PostsService(postRepository);

        postService.addNewPost(post);
        verify(postRepository, times(1)).save(post);

    }


    @DisplayName("when delete post get status code 200 OK, and deleted post as Json")
    @Test
    void deletePostTest() throws Exception {
        when(postRepository.existsById(1L)).thenReturn(true);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        mockmvc.perform(delete(urlForDeleteAndPut, post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(post)));

    }

    @DisplayName("when update post get status code 200 OK, and post before update as Json")
    @Test
    void updatePostTest() throws Exception {
        Post updatedPost = Post.builder()
                .id(1L)
                .title("nothing important")
                .content("nothing special")
                .build();

        when(postRepository.existsById(1L)).thenReturn(true);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        mockmvc.perform(put(urlForDeleteAndPut, post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPost)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(post)));
    }


    @DisplayName("find by title return 200 OK and list of Posts with required title")
    @Test
    void findPostByTitleTest() throws Exception {
        String title = "something important";

        when(postRepository.findAllByTitle(title)).thenReturn(expectedPosts);

        mockmvc.perform(get(url).param("title", title))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPosts)));

    }

    @DisplayName("sort by title test")
    @Test
    void sortByTitleTest() throws Exception {
        List<Post> expected = List.of(post2, post);

        when(postRepository.findAll(Sort.by("title"))).thenReturn(expected);

        mockmvc.perform(get(url).param("sort", "title"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)));


    }

    @Test
    void getAllPostsWithStar() throws Exception {
        String starUrl = url.concat("/star");
        List<Post> starList = List.of(post);

        when(postRepository.findByStar()).thenReturn(starList);

        mockmvc.perform(get(starUrl))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(starList)));

    }


}