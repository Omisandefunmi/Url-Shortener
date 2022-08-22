package com.urlShortener.UrlShortener.web.exceptions;

public class InvalidUrlFormatException extends UrlShortenerException {
    public InvalidUrlFormatException(String message) {
        super(message);
    }

    public InvalidUrlFormatException(String message, Throwable cause) {
        super(message, cause);

    }
}
