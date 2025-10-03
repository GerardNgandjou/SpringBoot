package com.example.blogpersonal.blogpersonal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean liked = false;

    // Many likes can be on one post
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonBackReference(value = "post-like")
    private Post post;

    // Many likes can be on one cmment
    @OneToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    @JsonBackReference(value = "comment-like")
    private Comment comment; 

    // Many likes can be made by one customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "customer_like")
    private Customer customer;
}
