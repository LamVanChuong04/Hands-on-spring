package com.elearning.demo.Service.ServiceImp;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Model.PostModel;
import com.elearning.demo.Model.Users;
import com.elearning.demo.Repository.PostRepository;
import com.elearning.demo.Repository.UserRepository;
import com.elearning.demo.Service.IService.IPostService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements IPostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostServiceImp(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createPost(PostDto postDto) {
        PostModel post = new PostModel();
        Users userProxy = userRepository.getReferenceById(postDto.getUserId());

        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        post.setUser(userProxy);

        postRepository.save(post);
    }

    @Override
    public List<PostResponse> getAllPosts() {
        List<PostModel> posts = postRepository.findAll();

        return posts.stream().map(post -> {
            PostResponse postResponse = new PostResponse();
            postResponse.setPostContent(post.getPostContent());
            postResponse.setPostTitle(post.getPostTitle());
            postResponse.setUserId(post.getUser().getId());
            return postResponse;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updatePost(Long postId,  PostDto postDto) {
        PostModel post = postRepository.findById(postId).orElseThrow(
                ()-> new RuntimeException("Khong tim thay bai post voi id = "+ postId));
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
    }
}



