package com.example.blogpersonal.blogpersonal.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogpersonal.blogpersonal.model.Comment;
import com.example.blogpersonal.blogpersonal.service.CommentService;

import lombok.Getter;

@Getter
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addcom")
    public Comment commentAdd(
        @RequestBody Comment comm
    ) {
        return commentService.savedComment(comm);
    }

}
