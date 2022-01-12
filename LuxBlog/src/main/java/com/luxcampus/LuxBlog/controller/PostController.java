package com.luxcampus.LuxBlog.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

    @GetMapping
    public String hello(){
        return "Hello";
    }
}
