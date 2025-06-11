package com.mysite.kyobo;

public class DataNotFoundException extends RuntimeException {
	public DataNotFoundException(String message) {
        super(message);
    }
}
