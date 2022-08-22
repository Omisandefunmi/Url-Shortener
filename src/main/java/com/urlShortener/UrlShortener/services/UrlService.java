package com.urlShortener.UrlShortener.services;

import com.urlShortener.UrlShortener.dtos.requests.ShortUrlRequest;
import com.urlShortener.UrlShortener.dtos.response.ShortUrlResponse;
import com.urlShortener.UrlShortener.web.exceptions.InvalidUrlFormatException;
import com.urlShortener.UrlShortener.web.exceptions.UrlShortenerException;

public interface UrlService {
    ShortUrlResponse generateShortUrl(ShortUrlRequest request) throws InvalidUrlFormatException;
    String updateShortenedUrl(String shortenedUrl, String customUrl) throws UrlShortenerException;
    String getShortenedUrl(String actualUrl) throws UrlShortenerException;
    String getActualUrl(String shortenedUrl) throws UrlShortenerException;
    void deleteUrlDetailsByShortenedUrl(String shortenedUrl) throws UrlShortenerException;
    void deleteUrlDetailsByActualUrl(String actualUrl) throws UrlShortenerException;
    long count();

}
