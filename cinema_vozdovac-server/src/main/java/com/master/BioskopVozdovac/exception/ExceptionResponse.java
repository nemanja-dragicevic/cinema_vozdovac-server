package com.master.BioskopVozdovac.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse extends RuntimeException {

    private String title;
    private String message;

}
