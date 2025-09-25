package com.example.blogpersonal.blogpersonal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blogpersonal.blogpersonal.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitle (String title);

}
