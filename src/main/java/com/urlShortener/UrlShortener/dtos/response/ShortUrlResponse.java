package com.urlShortener.UrlShortener.dtos.response;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ShortUrlResponse {
    private String actualUrl;
    private String shortenedUrl;
}
