package me.sideproject.urlshortener.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Base62Test {

  private final Base62 base62 = new Base62();

  @Test
  void testEncodeWithZeroNumber() {
    // given
    int number = 0;

    // when
    String encoded = base62.encode(number);

    // then
    assertThat(encoded).isEqualTo("0");
  }

  @Test
  void testEncodeWithSmallNumber() {
    // given
    int number = 123;

    // when
    String encoded = base62.encode(number);

    // then
    assertThat(encoded).isEqualTo("1z");
  }

  @Test
  void testEncodeWithLargeNumber() {
    // given
    int number = 140557;

    // when
    String encoded = base62.encode(number);

    // then
    assertThat(encoded).isEqualTo("aZ3");
  }

  @Test
  void testDecodeWithSmallLengthEncodedString() {
    // given
    String encoded = "aZ3";

    // when
    long decoded = base62.decode(encoded);

    // then
    assertThat(decoded).isEqualTo(140557);
  }

  @Test
  void testDecodeWithLargeLengthEncodedString() {
    // given
    String encoded = "yUabuiOa";

    // when
    long decoded = base62.decode(encoded);

    // then
    assertThat(decoded).isEqualTo(213034424463412L);
  }
}