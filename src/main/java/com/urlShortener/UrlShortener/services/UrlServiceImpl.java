package com.urlShortener.UrlShortener.services;

import com.google.common.hash.Hashing;
import com.urlShortener.UrlShortener.data.model.UrlDetails;
import com.urlShortener.UrlShortener.data.repository.UrlRepository;
import com.urlShortener.UrlShortener.dtos.requests.ShortUrlRequest;
import com.urlShortener.UrlShortener.dtos.response.ShortUrlResponse;
import com.urlShortener.UrlShortener.web.exceptions.InvalidUrlFormatException;
import com.urlShortener.UrlShortener.web.exceptions.UrlShortenerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class UrlServiceImpl implements UrlService{
    @Autowired
    private UrlRepository urlRepository;
    @Override
    public ShortUrlResponse generateShortUrl(ShortUrlRequest request) throws InvalidUrlFormatException {
        if(!isValid(request.getActualUrl())){
            throw new InvalidUrlFormatException("Invalid Url");
        }
        String encodedUrl = encodeUrl(request.getActualUrl());
        UrlDetails urlDetails = UrlDetails.builder()
                .actualUrl(request.getActualUrl())
                .shortenedUrl(encodedUrl)
                .dateCreated(LocalDateTime.now())
                .build();
        UrlDetails savedUrls = urlRepository.save(urlDetails);
        ShortUrlResponse shortUrlResponse = new ShortUrlResponse();
        shortUrlResponse.setActualUrl(savedUrls.getActualUrl());
        shortUrlResponse.setShortenedUrl(savedUrls.getShortenedUrl());
        return shortUrlResponse;
    }

    @Override
    public String updateShortenedUrl(String shortenedUrl, String customUrl) throws UrlShortenerException {
        UrlDetails urlDetails = urlRepository.findUrlDetailsByShortenedUrl(shortenedUrl).orElseThrow(() -> new UrlShortenerException("Url details not found"));
        if(!isValid(customUrl)){
            throw new InvalidUrlFormatException("Invalid url format");
        }
        urlDetails.setShortenedUrl(customUrl);
        return urlDetails.getShortenedUrl();
    }
    @Override
    public String getActualUrl(String shortenedUrl) throws UrlShortenerException {
        UrlDetails urlDetails = urlRepository.findUrlDetailsByShortenedUrl(shortenedUrl).orElseThrow(() -> new UrlShortenerException("Url details not found"));
        return urlDetails.getActualUrl();
    }

    @Override
    public String getShortenedUrl(String actualUrl) throws UrlShortenerException {
        UrlDetails urlDetails = urlRepository.findUrlDetailsByActualUrl(actualUrl).orElseThrow(() -> new UrlShortenerException("Url details not found"));
        return urlDetails.getShortenedUrl();
    }


    @Override
    public void deleteUrlDetailsByActualUrl(String actualUrl) throws UrlShortenerException {
        UrlDetails urlDetails = urlRepository.findUrlDetailsByActualUrl(actualUrl).orElseThrow(() -> new UrlShortenerException("Url details not found"));
        urlRepository.delete(urlDetails);
    }

    @Override
    public void deleteUrlDetailsByShortenedUrl(String shortenedUrl) throws UrlShortenerException {
        UrlDetails urlDetails = urlRepository.findUrlDetailsByShortenedUrl(shortenedUrl).orElseThrow(() -> new UrlShortenerException("Url details not found"));
        urlRepository.delete(urlDetails);
    }

    @Override
    public long count() {
        return urlRepository.count();
    }
    private boolean isValid(String actualUrl) {
        String regex =  "((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(actualUrl);
        return matcher.matches();
    }

    private String encodeUrl(String actualUrl) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Hashing.murmur3_32().hashString(actualUrl.concat(localDateTime.toString()), StandardCharsets.UTF_8).toString();
    }
}
