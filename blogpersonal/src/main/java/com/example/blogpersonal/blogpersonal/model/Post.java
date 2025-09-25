package com.example.blogpersonal.blogpersonal.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    
    // Many posts can belong to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonBackReference(value = "customer-post")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonManagedReference(value = "post-like")
    private List<Like> like = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "postCom")
    @JsonManagedReference(value = "post-comment")
    private List<Comment> comment = new ArrayList<>();

}
