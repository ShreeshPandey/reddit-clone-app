package com.shreeApp.reddit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.Subreddit;
import com.shreeApp.reddit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllBySubreddit(Subreddit subreddit);
	
	List<Post> findByUser(User user);

}
