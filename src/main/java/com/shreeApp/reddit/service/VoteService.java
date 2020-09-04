package com.shreeApp.reddit.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shreeApp.reddit.dto.VoteDto;
import com.shreeApp.reddit.exceptions.PostNotFoundException;
import com.shreeApp.reddit.exceptions.SpringRedditException;
import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.Vote;
import com.shreeApp.reddit.repository.PostRepository;
import com.shreeApp.reddit.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.shreeApp.reddit.enumeration.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
	
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;

	public void vote(VoteDto voteDto) throws SpringRedditException {
		System.out.println("postId:"+voteDto.getPostId());
		Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post Not Found with Id: " +voteDto.getPostId() ));
		//System.out.println("post::"+post);
		
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
		}
		
		if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

}
