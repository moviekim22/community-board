package com.example.board.comment;

import com.example.board.exception.DataNotFoundException;
import com.example.board.post.Post;
import com.example.board.post.PostRepository;
import com.example.board.user.SiteUser;
import com.example.board.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentRepository commentRepository;
    public final PostRepository postRepository;
    public final UserRepository userRepository;

    public void createComment(Long postId, String content, String username) {
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post not found"));

        SiteUser author = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(author);

        this.commentRepository.save(comment);
    }
}
