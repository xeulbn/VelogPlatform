package org.example.velogplatform.service;

import org.example.velogplatform.model.Post;
import org.example.velogplatform.model.User;
import org.example.velogplatform.repository.PostRepository;
import org.example.velogplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post, String username) {
        User user = userRepository.findByUsername(username);
        post.setAuthor(user.getUsername());
        return postRepository.save(post);
    }
}