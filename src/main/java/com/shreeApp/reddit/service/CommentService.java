package com.shreeApp.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shreeApp.reddit.dto.CommentsDto;
import com.shreeApp.reddit.exceptions.PostNotFoundException;
import com.shreeApp.reddit.exceptions.SpringRedditException;
import com.shreeApp.reddit.mapper.CommentMapper;
import com.shreeApp.reddit.model.Comment;
import com.shreeApp.reddit.model.NotificationEmail;
import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.User;
import com.shreeApp.reddit.repository.CommentRepository;
import com.shreeApp.reddit.repository.PostRepository;
import com.shreeApp.reddit.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {

	private static final String COMMENTED_ON_YOUR_POST = " Commented on your post.";
	private static final String POSTED_COMMENT_ON_POST = " posted a comment on your post.";
	private static final String POST_URL = "";
	
	
	private final PostRepository postRepository;	
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;

	public void save(CommentsDto commentsDto) throws SpringRedditException {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder.build(post.getUser().getUsername() + POSTED_COMMENT_ON_POST + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) throws SpringRedditException {
		mailService
				.sendMail(new NotificationEmail(user.getUsername() + COMMENTED_ON_YOUR_POST, user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));		
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
	}

}
