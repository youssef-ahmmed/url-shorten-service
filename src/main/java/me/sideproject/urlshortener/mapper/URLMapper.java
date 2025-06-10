package me.sideproject.urlshortener.mapper;

import me.sideproject.urlshortener.dto.URLOperationResponse;
import me.sideproject.urlshortener.models.URLMap;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class URLMapper implements Function<URLMap, URLOperationResponse> {
  @Override
  public URLOperationResponse apply(URLMap urlMap) {
    return new URLOperationResponse(
        urlMap.getOriginalURL(),
        urlMap.getShortCode(),
        urlMap.getCreatedAt(),
        urlMap.getUpdatedAt()
    );
  }
}
