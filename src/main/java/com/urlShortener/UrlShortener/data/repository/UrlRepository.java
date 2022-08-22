package com.urlShortener.UrlShortener.data.repository;

import com.urlShortener.UrlShortener.data.model.UrlDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<UrlDetails, String> {
    Optional<UrlDetails> findUrlDetailsByShortenedUrl(String shortenedUrl);

    Optional<UrlDetails> findUrlDetailsByActualUrl(String actualUrl);
}
