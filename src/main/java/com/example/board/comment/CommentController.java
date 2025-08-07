package com.example.board.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Long postId, @RequestParam("content") String content, Principal principal) {
        this.commentService.createComment(postId, content, principal.getName());
        return String.format("redirect:/post/detail/%s", postId);
    }
}
