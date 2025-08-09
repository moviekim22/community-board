package com.example.board.post;

import com.example.board.user.SiteUser;
import com.example.board.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Post> paging = postService.getList(page);
        model.addAttribute("paging", paging);
        return "post_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPost(id);

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());

        model.addAttribute("postForm", postForm);
        model.addAttribute("post", post);

        return "post_edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid PostForm postForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "post_edit";
        }

        // 현재 로그인한 사용자와 게시글의 작성자가 동일한지 확인
        Post post = postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        }

        postService.editPost(id, postForm.getTitle(), postForm.getContent());

        return String.format("redirect:/post/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {
        // 현재 로그인한 사용자와 게시글의 작성자가 동일한지 확인
        Post post = postService.getPost(id);
        if (!post.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        }

        postService.deletePost(post);

        return "redirect:/post/list";
    }
}
