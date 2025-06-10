package me.sideproject.urlshortener.service;

import jakarta.transaction.Transactional;
import me.sideproject.urlshortener.dto.URLMapDTO;
import me.sideproject.urlshortener.dto.URLMapStats;
import me.sideproject.urlshortener.dto.URLRequest;
import me.sideproject.urlshortener.exceptions.URLNotFound;
import me.sideproject.urlshortener.mapper.URLMapper;
import me.sideproject.urlshortener.mapper.URLStatsMapper;
import me.sideproject.urlshortener.models.URLMap;
import me.sideproject.urlshortener.repository.URLMapRepository;
import me.sideproject.urlshortener.utils.Base62;
import org.springframework.stereotype.Service;

@Service
public class URLService {

  private final URLMapRepository urlMapRepository;
  private final Base62 base62;
  private final URLMapper urlMapper;
  private final URLStatsMapper urlStatsMapper;

  public URLService(
      URLMapRepository urlMapRepository,
      Base62 base62,
      URLMapper urlMapper,
      URLStatsMapper urlStatsMapper
  ) {
    this.urlMapRepository = urlMapRepository;
    this.base62 = base62;
    this.urlMapper = urlMapper;
    this.urlStatsMapper = urlStatsMapper;
  }

  @Transactional
  public URLMapDTO shortenURL(URLRequest request) {
    String originalUrl = request.url();
    URLMap newUrl = URLMap
        .builder()
        .originalURL(originalUrl)
        .build();

    URLMap urlMap = urlMapRepository.save(newUrl);

    String shortCode = base62.encode(urlMap.getId());

    urlMap.setShortCode(shortCode);

    URLMap updatedURLMap = urlMapRepository.save(urlMap);

    return urlMapper.apply(updatedURLMap);
  }

  public URLMapDTO getURL(String shortCode) {
    URLMapDTO urlMap = urlMapRepository.findByShortCode(shortCode)
        .map(urlMapper)
        .orElseThrow(() -> new URLNotFound(shortCode));

    urlMapRepository.incrementClickCount(shortCode);
    return urlMap;
  }

  public URLMapDTO updateURL(String shortCode, URLRequest request) {
    URLMap urlMap = urlMapRepository.findByShortCode(shortCode)
        .orElseThrow(() -> new URLNotFound(shortCode));

    String originalUrl = request.url();
    urlMap.setOriginalURL(originalUrl);

    URLMap updatedURLMap = urlMapRepository.save(urlMap);

    return urlMapper.apply(updatedURLMap);
  }

  public void deleteURL(String shortCode) {
    URLMap urlMap = urlMapRepository.findByShortCode(shortCode)
        .orElseThrow(() -> new URLNotFound(shortCode));

    urlMapRepository.delete(urlMap);
  }

  public URLMapStats getURLStats(String shortCode) {
    URLMap urlMap = urlMapRepository.findByShortCode(shortCode)
        .orElseThrow(() -> new URLNotFound(shortCode));

    return urlStatsMapper.apply(urlMap);
  }
}
