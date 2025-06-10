package me.sideproject.urlshortener.service;

import jakarta.transaction.Transactional;
import me.sideproject.urlshortener.dto.URLRequest;
import me.sideproject.urlshortener.dto.URLOperationResponse;
import me.sideproject.urlshortener.exceptions.URLNotFound;
import me.sideproject.urlshortener.mapper.URLMapper;
import me.sideproject.urlshortener.models.URLMap;
import me.sideproject.urlshortener.repository.URLMapRepository;
import me.sideproject.urlshortener.utils.Base62;
import org.springframework.stereotype.Service;

@Service
public class URLService {

  private final URLMapRepository urlMapRepository;
  private final Base62 base62;
  private final URLMapper urlMapper;

  public URLService(URLMapRepository urlMapRepository, Base62 base62, URLMapper urlMapper) {
    this.urlMapRepository = urlMapRepository;
    this.base62 = base62;
    this.urlMapper = urlMapper;
  }

  @Transactional
  public URLOperationResponse shortenURL(URLRequest request) {
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

  public URLOperationResponse getURL(String shortCode) {
    return urlMapRepository.findByShortCode(shortCode)
        .map(urlMapper)
        .orElseThrow(() -> new URLNotFound(shortCode));
  }

  public URLOperationResponse updateURL(String shortCode, URLRequest request) {
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
}
