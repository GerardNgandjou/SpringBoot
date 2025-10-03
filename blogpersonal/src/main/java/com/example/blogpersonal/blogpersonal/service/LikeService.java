package com.example.blogpersonal.blogpersonal.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Like> findAllLikes() {
        return likeRepository.findAll()
                    .stream()
                    .collect(Collectors.toList());
    }

    public Like findAllById(Long id) {
        return likeRepository.findById(id)
                    .orElse(null);
    }

    public void deleteLikeById(Long id) {
        likeRepository.deleteById(id);
    }
    
}