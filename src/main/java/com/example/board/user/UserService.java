package com.example.board.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.board.exception.DataNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(String username, String password, String email){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        userRepository.save(user);
    }

    public SiteUser getUser(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("사이트 유저를 찾을 수 없음."));
    }
}
