package me.sideproject.urlshortener.dto;

public record URLStatusResponse(
    Boolean status,
    String message
) {
}
