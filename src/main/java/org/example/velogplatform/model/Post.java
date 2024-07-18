package org.example.velogplatform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name="post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String tags;
    private String content;
    @Column(name="image_url")
    private String imageUrl;
    private String author;
    private boolean published;

    @Column(name="create_at")
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column(name="update_at")
    private LocalDateTime updatedAt;

}
