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

import com.example.blogpersonal.blogpersonal.model.Like;
import com.example.blogpersonal.blogpersonal.service.LikeService;

import lombok.Getter;

@Getter
@RestController
@RequestMapping("/like")
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

    @GetMapping("/display_like")
    public List<Like> displayLike() {
        return likeService.findAllLikes();
    }

    @GetMapping("/find_like/{id}")
    public Like getLikeById(
        @PathVariable Long id
    ) {
        return likeService.findAllById(id);
    }

    @DeleteMapping("/delete_like/{idlike}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteLike(
        @PathVariable("idlike") Long id 
    ) {
        likeService.deleteLikeById(id);
    }

}
