package com.elearning.demo;

import com.elearning.demo.Model.PostModel;
import com.elearning.demo.Repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}
    @Autowired
    private PostRepository postRepository;

    @Test
    void testOptimisticLocking() throws InterruptedException {
        // 1. Tạo dữ liệu mẫu
        PostModel post = new PostModel();
        post.setPostTitle("Gốc");
        post = postRepository.save(post);
        Long postId = post.getPostId();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Dùng CountDownLatch để bắt JUnit phải đợi cả 2 luồng xong mới kết thúc
        CountDownLatch latch = new CountDownLatch(2);

        // LUỒNG A (Chạy chậm - Sẽ bị lỗi Version)
        executor.execute(() -> {
            try {
                PostModel postA = postRepository.findById(postId).get();
                Thread.sleep(1000); // Ngủ để luồng B thắng trước
                postA.setPostTitle("Admin A");
                postRepository.save(postA);
            } catch (Exception e) {
                System.out.println(">>> LUỒNG A THẤT BẠI: " + e.getClass().getSimpleName());
            } finally {
                latch.countDown();
            }
        });

        // LUỒNG B (Chạy nhanh - Sẽ thắng)
        executor.execute(() -> {
            try {
                PostModel postB = postRepository.findById(postId).get();
                postB.setPostTitle("Admin B");
                postRepository.save(postB);
                System.out.println(">>> LUỒNG B THÀNH CÔNG");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        latch.await(); // Chờ cả 2 luồng làm xong việc
        executor.shutdown();
    }

}
