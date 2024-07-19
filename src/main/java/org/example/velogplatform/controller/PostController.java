package org.example.velogplatform.controller;


import org.example.velogplatform.model.Post;
import org.example.velogplatform.service.PostService;
import org.example.velogplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping("/postform")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "blog/postform";
    }

    @PostMapping
    public String createPost(@ModelAttribute Post post, Authentication authentication) {
        String username = authentication.getName();
        postService.createPost(post, username);
        return "redirect:/";
    }

    @GetMapping("/{username}")
    public String getPostsByUser(@PathVariable String username, Model model) {
        model.addAttribute("posts", postService.getPostsByAuthor(username));
        return "blog/mypage";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return "redirect:/";
    }

    @GetMapping("/about")
    public String about(){
        return "blog/about";
    }


}
