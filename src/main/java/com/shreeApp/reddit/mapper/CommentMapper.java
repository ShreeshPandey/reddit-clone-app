package com.shreeApp.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shreeApp.reddit.dto.CommentsDto;
import com.shreeApp.reddit.model.Comment;
import com.shreeApp.reddit.model.Post;
import com.shreeApp.reddit.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target="id",ignore=true)
	@Mapping(target ="text",source="commentsDto.text")
	@Mapping(target ="createdDate",expression = "java(java.time.Instant.now())")
	@Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
	Comment map(CommentsDto commentsDto,Post post,User user);
	
	@Mapping(target ="postId",expression = "java(comment.getPost().getPostId())")
	@Mapping(target ="userName",expression = "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);

}
