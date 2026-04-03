package com.elearning.demo.Service.ServiceImp;

import com.elearning.demo.Dto.Request.PostDto;
import com.elearning.demo.Model.Posts;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class TestPessimisticLockService {

    private final PostServiceImp postServiceImp;
    public TestPessimisticLockService(PostServiceImp postServiceImp) {
        this.postServiceImp = postServiceImp;
    }
    @CacheEvict(value = "posts", allEntries = true)
    public void testPessimisticLocking(Long postId, PostDto postDto) throws InterruptedException {
        // 2 thread
        Thread th1 = new Thread(()->{
            try{
                Posts post = postServiceImp.updatePostWithPessimistic(postId, postDto);
                System.out.println(Thread.currentThread().getName()+ " Currently version "+ post.getVersion());
            }catch (RuntimeException ex){
                System.out.println(Thread.currentThread().getName() + "Updated failed : " + ex.getMessage());
            }
        });

        Thread th2 = new Thread(()->{
            try{
                Posts post = postServiceImp.updatePostWithPessimistic(postId, postDto);
                System.out.println(Thread.currentThread().getName()+ " Currently version "+ post.getVersion());
            }
            catch (RuntimeException ex){
                System.out.println(Thread.currentThread().getName() + "Updated failed : " + ex.getMessage());
            }
        });
        th1.start();
        th2.start();
        th1.join();
        th2.join();
    }

}
