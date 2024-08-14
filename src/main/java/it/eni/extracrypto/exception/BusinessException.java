package it.eni.extracrypto.exception;

import it.eni.extracrypto.model.ErrorEnum;
import org.springframework.web.server.ResponseStatusException;

public class BusinessException extends ResponseStatusException {

    public BusinessException(ErrorEnum errorEnum) {
        super (errorEnum.statusCode, errorEnum.message);
    }
}
