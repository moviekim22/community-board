package com.example.board.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "아이디를 입력하세요.")
    @Size(min = 5, max = 20)
    private String username;

    @NotEmpty(message = "이메일을 입력하세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password1;

    @NotEmpty(message = "비밀번호확인을 입력하세요.")
    private  String password2;
}
