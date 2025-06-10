package me.sideproject.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record URLRequest(
    @NotNull(message = "Original URL can't be null")
    @NotBlank(message = "Original URL can't be blank")
    String url
) {
}
