package com.example.board.post;

import com.example.board.exception.DataNotFoundException;
import com.example.board.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void create(String title, String content, SiteUser user) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);
        postRepository.save(post);
    }

    public Page<Post> getList(int page) {
        // 한 페이지에 10개 게시물, 생성일(createdAt) 기준 내림차순 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAll(pageable);
    }

    public Post getPost(Long id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    @Transactional // 트랜잭션 시작, 메서드 종료 시 커밋되면서 더티 체킹 발생
    public void editPost(Long id, String newTitle, String newContent) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Post not found"));

        post.setTitle(newTitle);
        post.setContent(newContent);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }
}
