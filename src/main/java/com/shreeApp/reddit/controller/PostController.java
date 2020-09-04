package com.shreeApp.reddit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreeApp.reddit.dto.PostRequest;
import com.shreeApp.reddit.dto.PostResponse;
import com.shreeApp.reddit.exceptions.SubredditNotFoundException;
import com.shreeApp.reddit.service.PostService;

import static org.springframework.http.ResponseEntity.status;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
	
	private PostService postService;

	@PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws SubredditNotFoundException {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) throws SubredditNotFoundException {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
