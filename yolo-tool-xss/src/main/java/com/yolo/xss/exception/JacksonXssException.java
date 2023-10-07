package com.yolo.xss.exception;

import lombok.Getter;

import java.io.IOException;

/**
 * xss jackson 异常
 */
@Getter
public class JacksonXssException extends IOException implements XssException {
	private final String name;
	private final String input;

	public JacksonXssException(String name, String input, String message) {
		super(message);
		this.name = name;
		this.input = input;
	}

}
