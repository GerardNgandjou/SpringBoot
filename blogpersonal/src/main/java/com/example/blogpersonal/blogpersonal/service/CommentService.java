package com.example.blogpersonal.blogpersonal.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Comment> findAllComment() {
        return commentRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    public Comment findAllById(Long id) {
        return commentRepository.findById(id)
                    .orElse(null);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

}
