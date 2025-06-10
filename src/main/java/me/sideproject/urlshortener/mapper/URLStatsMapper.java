package me.sideproject.urlshortener.mapper;

import me.sideproject.urlshortener.dto.URLMapStats;
import me.sideproject.urlshortener.models.URLMap;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class URLStatsMapper implements Function<URLMap, URLMapStats> {

  @Override
  public URLMapStats apply(URLMap urlMap) {
    return new URLMapStats(
        urlMap.getId(),
        urlMap.getOriginalURL(),
        urlMap.getShortCode(),
        urlMap.getCreatedAt(),
        urlMap.getUpdatedAt(),
        urlMap.getClickCount()
    );
  }
}
