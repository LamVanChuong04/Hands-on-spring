package com.elearning.demo.Service.IService;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Model.PostModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPostService {
    public void createPost(PostDto postDto);

    public List<PostResponse> findAllPosts();
}
