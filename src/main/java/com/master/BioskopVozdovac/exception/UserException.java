package com.master.BioskopVozdovac.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserException extends RuntimeException {

    private final String title;
    private final HttpStatus status;

    public UserException(String message, HttpStatus status) {
        super(message);
        this.title = "USER_ERROR";
        this.status = status;
    }

}
