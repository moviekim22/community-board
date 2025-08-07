package com.example.board.post;

import com.example.board.user.SiteUser;
import com.example.board.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/create")
    public String create(PostForm postForm) {
        return "post_form";
    }

    @PostMapping("/create")
    public String create(@Valid PostForm postForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }

        SiteUser user = userService.getUser(principal.getName());
        postService.create(postForm.getTitle(), postForm.getContent(), user);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> postList = postService.getList();
        model.addAttribute("postList", postList);
        return "post_list";
    }
}
