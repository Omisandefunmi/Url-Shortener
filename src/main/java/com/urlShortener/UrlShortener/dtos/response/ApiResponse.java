package com.urlShortener.UrlShortener.dtos.response;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ApiResponse {
    private boolean isSuccessful;
    private String message;

    public ApiResponse(boolean isSuccessful, String message) {
        this.isSuccessful = isSuccessful;
        this.message = message;
    }
}
