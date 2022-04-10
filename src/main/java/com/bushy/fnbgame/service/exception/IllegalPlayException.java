package com.bushy.fnbgame.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IllegalPlayException extends ResponseStatusException {
    public IllegalPlayException(){
        super(HttpStatus.BAD_REQUEST, "You cannot play from MainBucket pit");
    }
}
