package com.elearning.demo.Repository;

import com.elearning.demo.Dto.Response.UserResponse;
import com.elearning.demo.Model.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
//   cách 1: dùng join fetch
//    @Query(value = "select u from Users u join fetch u.posts")
//    List<Users> findAllUsers();

//    cách 2: dùng entity graph
    @EntityGraph(attributePaths = {"posts"})
    @Query(value = "select u from Users u")
    List<Users> findAllUsers();

//    cách 3: dùng batch(size = ?) - data loader: dùng in or = any
}
