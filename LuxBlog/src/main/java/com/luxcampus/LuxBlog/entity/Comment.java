package com.luxcampus.LuxBlog.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId", nullable = false)
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "creationDate")
    @CreationTimestamp
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;



}