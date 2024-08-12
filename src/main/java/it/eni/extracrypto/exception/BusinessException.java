package it.eni.extracrypto.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class BusinessException extends ResponseStatusException {
    @Getter
    private String message;


    public BusinessException(HttpStatusCode status,String message) {
        super(status);
        this.message = message;
    }
}
