package com.urlShortener.UrlShortener.dtos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShortUrlResponse {
    private String actualUrl;
    private String shortenedUrl;
}
