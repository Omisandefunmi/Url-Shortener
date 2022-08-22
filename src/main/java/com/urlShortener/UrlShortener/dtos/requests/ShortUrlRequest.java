package com.urlShortener.UrlShortener.dtos.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShortUrlRequest {
    private String actualUrl;
}
