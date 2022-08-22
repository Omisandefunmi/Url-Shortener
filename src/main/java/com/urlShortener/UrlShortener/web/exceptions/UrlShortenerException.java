package com.urlShortener.UrlShortener.web.exceptions;

public class UrlShortenerException extends Exception {
    public UrlShortenerException(String message){
        super(message);
    }
    public UrlShortenerException(String message, Throwable cause) {
        super(message, cause);
    }

}
