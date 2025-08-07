package com.example.board.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "게시글 제목을 입력하세요.")
    private String title;

    @NotEmpty(message = "내용을 작성하세요.")
    private String content;
}
