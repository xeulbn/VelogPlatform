package org.example.velogplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserPostResponse {


    private String postId;
    private String title;
    private String excerpt;
    private LocalDateTime createdAt;

}


