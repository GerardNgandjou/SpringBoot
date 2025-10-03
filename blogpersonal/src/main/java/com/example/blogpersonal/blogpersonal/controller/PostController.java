package com.example.blogpersonal.blogpersonal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/display_post")
    public List<Post> displayPosts() {
        return postService.findAllPost();
    }

    @GetMapping("/find_post/{id}")
    public Post getPostById(
        @PathVariable Long id
    ) {
        return postService.findAllById(id);
    }

    @DeleteMapping("/delete_post/{idpost}")    
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePost(
        @PathVariable("idpost") Long id 
    ) {
        postService.deletePostById(id);
    }

}
