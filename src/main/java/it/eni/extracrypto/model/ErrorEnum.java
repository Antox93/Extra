package it.eni.extracrypto.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorEnum {
    NAME_ALREADY_USED("Nome già in uso", HttpStatus.BAD_REQUEST);

    public final String message;
    public final HttpStatusCode statusCode;

    ErrorEnum(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
