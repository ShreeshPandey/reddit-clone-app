package com.shreeApp.reddit.enumeration;

import java.util.Arrays;

import com.shreeApp.reddit.exceptions.SpringRedditException;

public enum VoteType {

	UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction) {
    }
        
     public static VoteType lookup(Integer direction) throws SpringRedditException {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SpringRedditException("Vote not found"));
    }
    
    
    public Integer getDirection() {
        return direction;
    }
}
