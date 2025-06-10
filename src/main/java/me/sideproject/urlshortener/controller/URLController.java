package me.sideproject.urlshortener.controller;

import jakarta.validation.Valid;
import me.sideproject.urlshortener.dto.*;
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
  public ResponseEntity<URLOperationResponse<URLMapDTO>> createURL(@Valid @RequestBody URLRequest request) {
    URLMapDTO data = urlService.shortenURL(request);

    return ResponseEntity.ok(new URLOperationResponse<>(
        true,
        "URL shortened successfully",
        data
    ));
  }

  @GetMapping("/shorten/{shortCode}")
  public ResponseEntity<URLOperationResponse<URLMapDTO>> getURL(@PathVariable String shortCode) {
    URLMapDTO data = urlService.getURL(shortCode);

    return ResponseEntity.ok(new URLOperationResponse<>(
        true,
        "URL found successfully",
        data
    ));
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<URLOperationResponse<URLMapDTO>> redirectURL(@PathVariable String shortCode) {
    String originalUrl = urlService.getURL(shortCode).url();

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(originalUrl));

    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

  @PutMapping("/shorten/{shortCode}")
  public ResponseEntity<URLOperationResponse<URLMapDTO>> updateURL(
      @PathVariable String shortCode,
      @RequestBody URLRequest request
  ) {
    URLMapDTO data = urlService.updateURL(shortCode, request);

    return ResponseEntity.ok(new URLOperationResponse<>(
        true,
        "URL updated successfully",
        data
    ));
  }

  @DeleteMapping("/shorten/{shortCode}")
  public ResponseEntity<URLStatusResponse> deleteURL(@PathVariable String shortCode) {
    urlService.deleteURL(shortCode);

    return ResponseEntity.ok(new URLStatusResponse(
        true,
        "URL with short code '" + shortCode + "' deleted successfully"
    ));
  }

  @GetMapping("/shorten/{shortCode}/stats")
  public ResponseEntity<URLOperationResponse<URLMapStats>> getURLStats(@PathVariable String shortCode) {
    URLMapStats data = urlService.getURLStats(shortCode);

    return ResponseEntity.ok(new URLOperationResponse<>(
        true,
        "URL stats found successfully",
        data
    ));
  }
}
