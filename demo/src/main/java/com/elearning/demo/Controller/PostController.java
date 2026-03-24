package com.elearning.demo.Controller;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Model.PostModel;
import com.elearning.demo.Service.ServiceImp.PostServiceImp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostServiceImp postServiceImp;
    public PostController(PostServiceImp postServiceImp) {
        this.postServiceImp = postServiceImp;
    }

    @PostMapping("/posts")
    public String post(@RequestBody PostDto postDto) {
        postServiceImp.createPost(postDto);
        return "Added post successfully";
    }

    @GetMapping("/posts")
    public List<PostResponse> findAllPosts() {
        return postServiceImp.getAllPosts();
    }

    @PutMapping("/posts/{id}")
    public String updatePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        postServiceImp.updatePost(id, postDto);
        return "Updated post successfully";
    }
}
