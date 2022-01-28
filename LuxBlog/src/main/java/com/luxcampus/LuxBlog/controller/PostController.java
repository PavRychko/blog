package com.luxcampus.LuxBlog.controller;


import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
    private final PostsService postsService;

    @Autowired
    public PostController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postsService.getPosts();
    }

    @PostMapping
    public void addNewPost(@RequestBody Post post) {
        postsService.addNewPost(post);
    }

    @PutMapping(path = "{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post updatedPost){
        return postsService.updatePost(id, updatedPost);
    }

    @DeleteMapping(path = "{id}")
    public Post deletePost(@PathVariable("id") Long id) {
        return postsService.deletePost(id);
    }

}
