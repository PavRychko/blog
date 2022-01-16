package com.luxcampus.LuxBlog.config;

import com.luxcampus.LuxBlog.entity.Post;
import com.luxcampus.LuxBlog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PostConfig {

    @Bean
    CommandLineRunner commandLineRunner(PostRepository postRepository) {
        return args -> {
            Post testPost = new Post(
                    "TestHeader",
                    "testText"
            );
            Post testPost2 = new Post(
                    "TestHeader2",
                    "testText2"
            );
            postRepository.saveAll(
                    List.of(testPost, testPost2)
            );
        };

    }
}
