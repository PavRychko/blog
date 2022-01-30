package com.luxcampus.LuxBlog.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "creation_date")
    @CreationTimestamp
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}