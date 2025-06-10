package me.sideproject.urlshortener.controller;

import jakarta.validation.Valid;
import me.sideproject.urlshortener.dto.URLOperationResponse;
import me.sideproject.urlshortener.dto.URLRequest;
import me.sideproject.urlshortener.dto.URLStatusResponse;
import me.sideproject.urlshortener.service.URLService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class URLController {
  private final URLService urlService;

  public URLController(URLService urlService) {
    this.urlService = urlService;
  }

  @PostMapping("/shorten")
  public ResponseEntity<URLOperationResponse> createURL(@Valid @RequestBody URLRequest request) {
    URLOperationResponse response = urlService.shortenURL(request);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/shorten/{shortCode}")
  public ResponseEntity<URLOperationResponse> getURL(@PathVariable String shortCode) {
    URLOperationResponse response = urlService.getURL(shortCode);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<URLOperationResponse> redirectURL(@PathVariable String shortCode) {
    String originalUrl = urlService.getURL(shortCode).url();

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(originalUrl));

    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

  @PutMapping("/shorten/{shortCode}")
  public ResponseEntity<URLOperationResponse> updateURL(
      @PathVariable String shortCode,
      @RequestBody URLRequest request
  ) {
    URLOperationResponse response = urlService.updateURL(shortCode, request);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/shorten/{shortCode}")
  public ResponseEntity<URLStatusResponse> deleteURL(@PathVariable String shortCode) {
    urlService.deleteURL(shortCode);

    return ResponseEntity.ok(new URLStatusResponse(
        true,
        "URL with short code '" + shortCode + "' deleted successfully"
    ));
  }
}
