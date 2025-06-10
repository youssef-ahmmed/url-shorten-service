package me.sideproject.urlshortener.dto;

import java.util.Date;

public record URLMapStats(
    Integer id,
    String url,
    String shortCode,
    Date createdAt,
    Date updatedAt,
    Integer clickCount
) {
}
