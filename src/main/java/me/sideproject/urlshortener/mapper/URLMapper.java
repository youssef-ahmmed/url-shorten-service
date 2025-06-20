package me.sideproject.urlshortener.mapper;

import me.sideproject.urlshortener.dto.URLMapDTO;
import me.sideproject.urlshortener.models.URLMap;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class URLMapper implements Function<URLMap, URLMapDTO> {
  @Override
  public URLMapDTO apply(URLMap urlMap) {
    return new URLMapDTO(
        urlMap.getOriginalURL(),
        urlMap.getShortCode(),
        urlMap.getCreatedAt(),
        urlMap.getUpdatedAt()
    );
  }
}
