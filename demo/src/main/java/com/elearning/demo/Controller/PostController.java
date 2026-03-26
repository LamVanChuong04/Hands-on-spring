package com.elearning.demo.Controller;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Model.PostModel;
import com.elearning.demo.Service.ServiceImp.PostServiceImp;
import com.elearning.demo.Service.ServiceImp.TestPessimisticLockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostServiceImp postServiceImp;
    private final TestPessimisticLockService testPessimisticLockService;
    public PostController(PostServiceImp postServiceImp,  TestPessimisticLockService testPessimisticLockService) {
        this.postServiceImp = postServiceImp;
        this.testPessimisticLockService = testPessimisticLockService;
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

    @PutMapping("/posts-test-optimistic/{id}")
    public String updatePost(@PathVariable Long id, @RequestBody PostDto postDto) throws InterruptedException {
        postServiceImp.testOptimisticLockingt(id, postDto);
        return "OPTIMISTIC LOCKING: -----------Updated post successfully";
    }
    @PutMapping("/posts-test-pessimistic/{id}")
    public String updatePostWithPessimistic(@PathVariable Long id, @RequestBody PostDto postDto) throws InterruptedException {
        testPessimisticLockService.testPessimisticLocking(id, postDto);
        return "PESSIMISTIC LOCKING: -----------Updated post successfully";
    }
}
