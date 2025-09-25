package com.example.blogpersonal.blogpersonal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogpersonal.blogpersonal.model.Post;
import com.example.blogpersonal.blogpersonal.service.PostService;

import lombok.Getter;

@Getter
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/addpos")
    public Post addPost (
        @RequestBody Post post
    ) {
        return postService.savedPost(post);
    }

}
