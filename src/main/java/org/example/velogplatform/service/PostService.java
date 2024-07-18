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

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post createPost(Post post, String username) {
        User user = userRepository.findByUsername(username);
        post.setAuthor(user.getUsername());
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElse(null);
        post.setTitle(postDetails.getTitle());
        post.setTags(postDetails.getTags());
        post.setContent(postDetails.getContent());
        post.setImageUrl(postDetails.getImageUrl());
        post.setAuthor(postDetails.getAuthor());
        post.setPublished(postDetails.isPublished());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        postRepository.delete(post);
    }
}
