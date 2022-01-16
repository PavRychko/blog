package com.luxcampus.LuxBlog.services;

import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PostsService {
    private final PostRepository postRepository;

    @Autowired
    public PostsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public void addNewPost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalStateException("post with id " + id + " does not exists");
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePost(Long id, Post updatedPost) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalStateException("post with id " + id + " does not exists"));
        String newTitle = updatedPost.getTitle();
        String newContent = updatedPost.getContent();
        if (newTitle != null && newTitle.length() > 0 && !Objects.equals(post.getTitle(), newTitle)) {
            post.setTitle(newTitle);
        }
        if (newContent != null && newContent.length() > 0 && !Objects.equals(post.getContent(), newContent)) {
            post.setContent(newContent);
        }
    }

}
