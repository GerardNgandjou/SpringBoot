package com.example.blogpersonal.blogpersonal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogpersonal.blogpersonal.model.Post;
import com.example.blogpersonal.blogpersonal.repository.PostRepository;

@Service
public class PostService {

    @Autowired 
    private final PostRepository postRepository;

    public PostService (PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savedPost (Post post) {
        var pos = postRepository.save(post);
        return pos;
    }

}
