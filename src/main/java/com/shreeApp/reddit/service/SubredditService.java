package com.shreeApp.reddit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shreeApp.reddit.dto.SubredditDto;
import com.shreeApp.reddit.exceptions.SpringRedditException;
//import com.shreeApp.reddit.mapper.SubredditMapper;
import com.shreeApp.reddit.model.Subreddit;
import com.shreeApp.reddit.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;
	//private final SubredditMapper subredditMapper;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit save = subredditRepository.save(mapSubredditDto(subredditDto));
		subredditDto.setId(save.getId());
		return subredditDto;
	}

	@Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(this::mapToDto)
				.collect(Collectors.toList());

	}

	public SubredditDto getSubreddit(Long id) throws SpringRedditException {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with Id: " + id));
		return mapToDto(subreddit);
	}

	
	  private SubredditDto mapToDto(Subreddit subreddit) { return
	  SubredditDto.builder().name(subreddit.getName())
	  .description(subreddit.getDescription()).build(); }
	  
	  private Subreddit mapSubredditDto(SubredditDto subredditDto) { return
	  Subreddit.builder().name(subredditDto.getName())
	  .description(subredditDto.getDescription()).build(); }
	 

}
