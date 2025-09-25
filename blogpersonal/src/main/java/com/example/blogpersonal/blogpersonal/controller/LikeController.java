package com.example.blogpersonal.blogpersonal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogpersonal.blogpersonal.model.Like;
import com.example.blogpersonal.blogpersonal.service.LikeService;

import lombok.Getter;

@Getter
@RestController("/like")
@RequestMapping
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/addlik")
    public Like addLike (
        @RequestBody Like like
    ) {
        return likeService.savedLike(like);
    }

}
