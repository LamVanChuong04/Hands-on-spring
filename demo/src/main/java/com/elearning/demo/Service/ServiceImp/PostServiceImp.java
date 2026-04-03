package com.elearning.demo.Service.ServiceImp;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Dto.Response.PostResponse;
import com.elearning.demo.Model.Posts;
import com.elearning.demo.Model.Users;
import com.elearning.demo.Repository.PostRepository;
import com.elearning.demo.Repository.UserRepository;
import com.elearning.demo.Service.IService.IPostService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class PostServiceImp implements IPostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostServiceImp(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @CacheEvict(value = "posts", allEntries = true)
    @Override
    @Transactional
    public void createPost(PostDto postDto) {
        Posts post = new Posts();
        Users userProxy = userRepository.getReferenceById(postDto.getUserId());

        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        post.setUser(userProxy);

        postRepository.save(post);
    }
    @Cacheable(value = "posts", key = "'all'")
    @Override
    public List<PostResponse> getAllPosts() {
        List<Posts> posts = postRepository.findAll();
        log.debug("inside getAllPosts() method");
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
    public Posts updatePost(Long postId, PostDto postDto) {

        Posts post = postRepository.findById(postId).orElseThrow(
                ()-> new RuntimeException("Khong tim thay bai post voi id = "+ postId));

        // thread current run
        System.out.println(Thread.currentThread().getName() + "fetch post with version " + post.getVersion());
        System.out.println("-------");
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());
        return postRepository.saveAndFlush(post);
    }
    @Transactional
    @Override
    public Posts updatePostWithPessimistic(Long postId, PostDto postDto) {
        System.out.println(Thread.currentThread().getName()+ " is watching post");

        Posts post = postRepository.findPostByIdAndLock(postId);

        System.out.println(Thread.currentThread().getName()+ " is watching post with version " + post.getVersion());
        post.setPostContent(postDto.getPostContent());
        post.setPostTitle(postDto.getPostTitle());

        return postRepository.save(post);

    }


    @Override
    public void testOptimisticLockingt(Long postId, PostDto postDto) throws InterruptedException{
        // 2 thread
        Thread th1 = new Thread(()->{
            try{
                System.out.println(Thread.currentThread().getName()+ " is watching post");
                Posts post = updatePost(postId, postDto);
                System.out.println(Thread.currentThread().getName()+ " updated post successful with version "+ post.getVersion());
            }catch (Exception ex){
                System.out.println(Thread.currentThread().getName() + " failed : " + ex.getMessage());
            }
        });

        Thread th2 = new Thread(()->{
            try{
                System.out.println(Thread.currentThread().getName()+ " is watching post");
                Posts post = updatePost(postId, postDto);
                System.out.println(Thread.currentThread().getName()+ " updated post successful with version "+ post.getVersion());
            }catch (Exception ex){
                System.out.println(Thread.currentThread().getName() + " failed : " + ex.getMessage());
            }
        });
        th1.start();
        th2.start();
        th1.join();
        th2.join();
    }




}



