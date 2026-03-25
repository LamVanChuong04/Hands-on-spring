package com.elearning.demo.Repository;

import com.elearning.demo.Model.PostModel;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
    @Query(value = "select p from PostModel p")
    List<PostModel> findAllPosts();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select p from PostModel p where p.postId = :postId")
    PostModel findPostByIdAndLock(Long postId);
}
