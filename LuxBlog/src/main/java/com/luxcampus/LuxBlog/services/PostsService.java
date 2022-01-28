package com.luxcampus.LuxBlog.services;

import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.List;



@Controller
@Slf4j
public class PostsService {
    private final PostRepository postRepository;

    @Autowired
    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        log.info("In Post Service getPosts");
        return postRepository.findAll();
    }

    public void addNewPost(Post post) {
        log.info("In Post Service addNewPost {}", post);
        postRepository.save(post);
    }

    public Post deletePost(Long id) {
        log.info("In Post Service deletePost by id {}", id);
        if (!postRepository.existsById(id)) {
            throw new IllegalStateException("post with id " + id + " does not exists");
        }
        Post oldPost = findById(id);
        postRepository.deleteById(id);
        return oldPost;
    }

    @Transactional
    public Post updatePost(Long id, Post updatedPost) {
        log.info("In Post Service updatePost {}", updatedPost);
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalStateException("post with id " + id + " does not exists"));
        String newTitle = updatedPost.getTitle();
        String newContent = updatedPost.getContent();
        Post oldPost = findById(id);
        post.setTitle(newTitle);
        post.setContent(newContent);
        return oldPost;
    }

    Post findById(Long id) {
        log.info("In Post Service findById {}", id);
        return postRepository.findById(id).get();
    }
}
