package com.shreeApp.reddit.exceptions;

public class SubredditNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubredditNotFoundException(String message) {
		super(message);
	}
}
