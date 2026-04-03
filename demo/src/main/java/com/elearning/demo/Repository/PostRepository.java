package com.elearning.demo.Repository;

import com.elearning.demo.Model.Posts;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long> {
    @Query(value = "select p from Posts p")
    List<Posts> findAllPosts();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select p from Posts p where p.postId = :postId")
    Posts findPostByIdAndLock(Long postId);
}
