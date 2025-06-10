package me.sideproject.urlshortener.dto;

import java.util.Date;

public record URLOperationResponse(
    String url,
    String shortCode,
    Date createdAt,
    Date updatedAt
) {
}
