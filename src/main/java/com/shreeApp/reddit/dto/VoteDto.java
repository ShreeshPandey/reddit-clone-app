package com.shreeApp.reddit.dto;

import com.shreeApp.reddit.enumeration.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
	private VoteType voteType;
    private Long postId;
}
