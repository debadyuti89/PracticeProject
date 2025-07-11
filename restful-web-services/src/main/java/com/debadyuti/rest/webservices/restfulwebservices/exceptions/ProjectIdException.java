package com.debadyuti.rest.webservices.restfulwebservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2720239192301377789L;

	public ProjectIdException(String message) {
        super(message);
    }
}
