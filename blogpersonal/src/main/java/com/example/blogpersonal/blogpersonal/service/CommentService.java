package com.example.blogpersonal.blogpersonal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blogpersonal.blogpersonal.model.Comment;
import com.example.blogpersonal.blogpersonal.repository.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment savedComment (Comment comm) {
        var com = commentRepository.save(comm);
        return com;
    }

}
