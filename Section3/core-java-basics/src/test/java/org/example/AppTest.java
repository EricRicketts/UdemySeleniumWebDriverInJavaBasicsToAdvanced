package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

  @Test
  @DisplayName("Java requires one must declare a variable as a specific type")
  public void testVariableTypes() {
    byte a = 5;
    short b = 6;
    int c = 10;
    long d = 200L;
    float e = 23.1F;
    double f = 45.687;
    char g = 'g';
    boolean h = true;
    String i = "foo"; // not a primitive but used extensively
    Object[] expected = {(byte) 5, (short) 6, 10, (long) 200, (float) 23.1, 45.687, 'g', true, "foo"};
    Object[] results = {a, b, c, d, e, f, g, h, i};
    assertArrayEquals(expected, results);
  }

  @Test
  @DisplayName("strings take precedence in concatenation")
  public void testStringConcatenation() {
    int x = 5;
    String y = "Foo Bars";
    String expected = "5 Foo Bars";
    String results = x + " " + y;
    assertEquals(expected, results);
  }
}