package com.shreeApp.reddit.exceptions;

public class SpringRedditException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
	
	public SpringRedditException(String message){
		super(message);
	}
}
