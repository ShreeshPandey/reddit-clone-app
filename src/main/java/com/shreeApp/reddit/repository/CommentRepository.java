package com.shreeApp.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shreeApp.reddit.model.Comment;
import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);

}
