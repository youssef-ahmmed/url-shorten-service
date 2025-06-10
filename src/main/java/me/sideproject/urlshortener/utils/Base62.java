package me.sideproject.urlshortener.utils;

import org.springframework.stereotype.Component;

@Component
public class Base62 {
  private final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public String encode(Integer number) {
    if (number < 0) {
      throw new IllegalArgumentException("Number must be non-negative.");
    }

    if (number == 0) return "0";

    StringBuilder result = new StringBuilder();
    while (number > 0) {
      int remainder = number % 62;
      number /= 62;
      result.append(BASE62.charAt(remainder));
    }

    return result.reverse().toString();
  }

  public Long decode(String encoded) {
    if (encoded == null || encoded.isEmpty()) {
      throw new IllegalArgumentException("Encoded string must not be null or empty.");
    }

    long number = 0;

    for (int i = 0; i < encoded.length(); i++) {
      number = number * 62 + BASE62.indexOf(encoded.charAt(i));
    }

    return number;
  }
}
