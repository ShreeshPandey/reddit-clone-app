package com.shreeApp.reddit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.User;
import com.shreeApp.reddit.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
