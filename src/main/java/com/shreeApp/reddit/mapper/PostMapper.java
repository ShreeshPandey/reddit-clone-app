package com.shreeApp.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.shreeApp.reddit.dto.PostRequest;
import com.shreeApp.reddit.dto.PostResponse;
import com.shreeApp.reddit.enumeration.VoteType;
import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.Subreddit;
import com.shreeApp.reddit.model.User;
import com.shreeApp.reddit.repository.CommentRepository;
import com.shreeApp.reddit.repository.VoteRepository;
import com.shreeApp.reddit.service.AuthService;

import java.util.Optional;

import static com.shreeApp.reddit.enumeration.VoteType.DOWNVOTE;
import static com.shreeApp.reddit.enumeration.VoteType.UPVOTE;

@Mapper(componentModel="spring")
public interface PostMapper {
	
	/*@Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;*/

	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	Post map(PostRequest postRequest, Subreddit subreddit, User user);
	
	@Mapping(target = "id", source = "postId")
	@Mapping(target = "userName", source = "user.username")
	@Mapping(target = "subredditName", source = "subreddit.name")	
	PostResponse mapToDto(Post post);
	
	/*@Mapping(target = "postName", source = "postName")
	@Mapping(target = "url", source = "url")
	@Mapping(target = "description", source = "description")*/
	
	
	
   /* @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

*/
    /*Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }*/

}
