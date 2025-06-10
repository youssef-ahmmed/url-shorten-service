package me.sideproject.urlshortener.exceptions;

public class URLNotFound extends RuntimeException {
  public URLNotFound(String shortCode) {
    super("URL with code '" + shortCode + "' not found");
  }
}
