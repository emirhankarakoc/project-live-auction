package com.karakoc.mezat.exceptions.general;

import com.karakoc.mezat.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public BadRequestException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }


}
