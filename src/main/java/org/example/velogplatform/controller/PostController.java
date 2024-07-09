package org.example.velogplatform.controller;

import org.example.velogplatform.model.Post;
import org.example.velogplatform.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/postform")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "blog/postform";
    }
}
