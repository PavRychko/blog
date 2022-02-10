package com.luxcampus.LuxBlog.controller;


import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
    private final PostsService postsService;

    @Autowired
    public PostController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam(required = false, value = "title") String title,
                                  @RequestParam(required = false, value = "sort") String sorter) {
        List<Post> posts;
        if (title != null) {
            posts = postsService.findPostByTitle(title);
        } else if (sorter != null && Objects.equals(sorter, "title")) {
            posts = postsService.findPostsSortedByTitle(Sort.by(sorter));
        } else {
            posts = postsService.getPosts();
        }
        return posts;
    }

    @GetMapping(path = "/star")
    public List<Post> getAllPostsWithStar() {
        return postsService.findAllWithStar();
    }

    @PostMapping
    public void addNewPost(@RequestBody Post post) {
        postsService.addNewPost(post);
    }

    @PutMapping(path = "{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post updatedPost) {
        return postsService.updatePost(id, updatedPost);
    }

    @PutMapping(path = "{id}/star")
    public void setStarTrue(@PathVariable("id") Long id) {
        postsService.setStarTrue(id);
    }

    @DeleteMapping(path = "{id}")
    public Post deletePost(@PathVariable("id") Long id) {
        return postsService.deletePost(id);
    }

    @DeleteMapping(path = "{id}/star")
    public void deleteStarFromPost(@PathVariable("id") Long id) {
        postsService.deleteStar(id);
    }

}
