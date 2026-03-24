package com.elearning.demo.Service;

import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Dto.Response.UserResponse;
import com.elearning.demo.Model.Users;
import com.elearning.demo.Repository.PostRepository;
import com.elearning.demo.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,  PostRepository postRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
    }

    public String addUser(Users user) {
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword())); // mã hóa đúng cách
        userRepository.save(newUser);
        return "Add user successfully!";
    }
    public List<UserResponse> getAllUsers() {
        List<Users> users = userRepository.findAll();

        return users.stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(user.getUsername());

            List< PostResponse> posts = user.getPosts().stream().map(post ->{
                PostResponse postResponse = new PostResponse();
                postResponse.setPostContent(post.getPostContent());
                postResponse.setPostTitle(post.getPostTitle());
                return postResponse;
            }).toList();

            userResponse.setPosts(posts);
            return userResponse;

        }).collect(Collectors.toList());
    }

    public Optional<Users> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}