package me.sideproject.urlshortener.dto;

import java.util.Date;

public record URLMapDTO(
    String url,
    String shortCode,
    Date createdAt,
    Date updatedAt
) {
}
