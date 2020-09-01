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

import com.shreeApp.reddit.dto.SubredditDto;
import com.shreeApp.reddit.exceptions.SpringRedditException;
import com.shreeApp.reddit.service.SubredditService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {

	private final SubredditService subredditService;
	
	@PostMapping
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
	}
	
	@GetMapping
	public ResponseEntity<List<SubredditDto>> getAllSubreddits()
	{
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id) throws SpringRedditException{
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
	}
}
