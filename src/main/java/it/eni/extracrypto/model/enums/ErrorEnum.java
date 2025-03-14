package it.eni.extracrypto.model.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorEnum {
    NAME_ALREADY_USED("Nome già in uso", HttpStatus.BAD_REQUEST),
    AMOUNT_EXCEEDED("Non hai abbastanza fondi", HttpStatus.BAD_REQUEST),
    COINMARKET_ERROR("Errore durante la chiamata a Coinmarketcap", HttpStatus.INTERNAL_SERVER_ERROR);

    public final String message;
    public final HttpStatusCode statusCode;

    ErrorEnum(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
