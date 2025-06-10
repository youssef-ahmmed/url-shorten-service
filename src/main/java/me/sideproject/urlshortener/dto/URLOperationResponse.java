package me.sideproject.urlshortener.dto;

public record URLOperationResponse<T>(
    Boolean status,
    String message,
    T data
) {
}
