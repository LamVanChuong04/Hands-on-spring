package com.elearning.demo.Service.IService;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Model.Posts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPostService {
    public void createPost(PostDto postDto);

    public List<PostResponse> getAllPosts();
    public Posts updatePost(Long postId, PostDto postDto);
    public Posts updatePostWithPessimistic(Long postId, PostDto postDto);
    // optimistic locking
    public void testOptimisticLockingt(Long postId, PostDto postDto) throws InterruptedException;


}
