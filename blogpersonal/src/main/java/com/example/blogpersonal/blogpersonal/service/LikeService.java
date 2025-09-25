package com.example.blogpersonal.blogpersonal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogpersonal.blogpersonal.model.Like;
import com.example.blogpersonal.blogpersonal.repository.LikeRepository;

@Service
public class LikeService {

    @Autowired
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like savedLike (Like like) {
        var lik = likeRepository.save(like);
        return lik;
    }
    
}
