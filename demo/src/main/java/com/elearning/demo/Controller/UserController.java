package com.elearning.demo.Controller;

import com.elearning.demo.Dto.Response.UserResponse;
import com.elearning.demo.Model.Users;
import com.elearning.demo.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    public UserController(UserService userService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/home-page")
    public String home(){
        return "HOME PAGE";
    }

    @PostMapping("/add")
    public void addUser(@RequestBody Users newUser){
        userService.addUser(newUser);
    }

    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return Map.of(
                "username", authentication.getName(),
                "roles", authentication.getAuthorities()
        );
    }

    @GetMapping("/users")
    public List<UserResponse> allUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public Optional<Users> getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }
}
