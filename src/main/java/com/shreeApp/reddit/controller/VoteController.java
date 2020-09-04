package com.shreeApp.reddit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreeApp.reddit.dto.VoteDto;
import com.shreeApp.reddit.exceptions.SpringRedditException;
import com.shreeApp.reddit.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> createVote(@RequestBody VoteDto voteDto) throws SpringRedditException {
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
