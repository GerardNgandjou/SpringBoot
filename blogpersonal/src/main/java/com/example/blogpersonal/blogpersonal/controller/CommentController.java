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

    @GetMapping("/display_comment")
    public List<Comment> displayComment() {
        return commentService.findAllComment();
    }

    @GetMapping("/find_comment/{id}")
    public Comment getCommentById(
        @PathVariable Long id
    ) {
        return commentService.findAllById(id);
    }

    @DeleteMapping("/delete_comment/{idcomment}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteComment(
        @PathVariable("idcomment") Long id 
    ) {
        commentService.deleteCommentById(id);
    }

}
