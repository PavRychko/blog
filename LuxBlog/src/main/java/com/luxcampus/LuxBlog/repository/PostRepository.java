package com.luxcampus.LuxBlog.repository;

import com.luxcampus.LuxBlog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTitle(String title);

    @Query("select p from Post p where p.star = true")
    List<Post> findByStar();


}
