package com.example.board;

import com.example.board.exception.DataNotFoundException;
import com.example.board.post.Post;
import com.example.board.post.PostRepository;
import com.example.board.post.PostService;
import com.example.board.user.SiteUser;
import com.example.board.user.UserService;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Test
	void contextLoads() {
	}

	@Test
	void createPost() {
		String username = "test123";
		SiteUser user = this.userService.getUser(username);

		for (int i=0; i < 100; i++) {
			this.postService.create(String.valueOf(i), String.valueOf(i), user);
		}
	}
}
