package com.urlShortener.UrlShortener.web.controller;

import com.urlShortener.UrlShortener.dtos.requests.ShortUrlRequest;
import com.urlShortener.UrlShortener.dtos.response.ApiResponse;
import com.urlShortener.UrlShortener.services.UrlService;
import com.urlShortener.UrlShortener.web.exceptions.InvalidUrlFormatException;
import com.urlShortener.UrlShortener.web.exceptions.UrlShortenerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping
public class UrlController {
       private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/generateShortLink")
    public ResponseEntity<?> generateShortUrl(ShortUrlRequest request){
        try{
            var serviceResponse = urlService.generateShortUrl(request);
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);

        } catch (InvalidUrlFormatException e) {
            ApiResponse apiResponse = new ApiResponse(false, e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @PatchMapping("/customiseLink")
    public ResponseEntity <?> customiseUrl(@RequestParam String shortenedUrl, @RequestParam String customiseUrl){
        try{
            var serviceResponse = urlService.updateShortenedUrl(shortenedUrl, customiseUrl);
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
        } catch (UrlShortenerException e) {
            ApiResponse apiResponse = new ApiResponse(false, e.getMessage());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get/{shortUrl}")
    public RedirectView redirectToFullUrl(@PathVariable String shortUrl) throws UrlShortenerException {
        try{
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(urlService.getActualUrl(shortUrl));
            return redirectView;
        } catch (UrlShortenerException e) {
            throw new UrlShortenerException(e.getMessage());
        }
    }

    @GetMapping("/get/get/{shortUrl}")
    public void redirectToFullUrl1(HttpServletResponse servletResponse, @PathVariable String shortUrl) throws UrlShortenerException, IOException {
         servletResponse.sendRedirect(urlService.getActualUrl(shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity <?> getFullUrl(@PathVariable String shortUrl) throws UrlShortenerException {
        try {
            return new ResponseEntity<>(urlService.getActualUrl(shortUrl), HttpStatus.FOUND);
        } catch (UrlShortenerException e) {
            ApiResponse response = new ApiResponse(false, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{actualUrl}")
    public void deleteUrl(@PathVariable String actualUrl) throws UrlShortenerException {
        urlService.deleteUrlDetailsByActualUrl(actualUrl);
    }

}
