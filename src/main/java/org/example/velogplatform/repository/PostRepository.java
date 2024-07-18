package org.example.velogplatform.repository;

import org.example.velogplatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Post findById(long id);
    List<Post> findTop5ByOrderByCreatedAtDesc();

}
