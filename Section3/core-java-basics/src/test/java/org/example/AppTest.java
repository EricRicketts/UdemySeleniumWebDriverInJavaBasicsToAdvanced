package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

  private ArrayList<String> list;
  private String[] expectedAry;
  @BeforeEach
  public void setUp() {
    list = new ArrayList<>(List.of("foo", "bar", "fizz", "buzz"));
    expectedAry = new String[]{"foo", "bar", "fizz", "buzz"};
  }

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

  @Test
  @DisplayName("arrays can hold more than one value")
  public void testArraysCanHoldMoreThanOneValue() {
    int[] ary;
    ary = new int[] {0, 1, 2, 3, 4};
    int[] expected = {0, 1, 2, 3, 4};
    assertArrayEquals(expected, ary);
  }

  @Test
  @DisplayName("iterate through an array")
  public void testIterateThroughAnArray() {
    int[] expected = {0, 1, 2, 3, 4};
    int[] results = new int[5];
    for (int i = 0; i < expected.length; i++) {
      results[i] = i;
    }

    assertArrayEquals(expected, results);
  }

  @Test
  @DisplayName("iterate through array with enhanced for loop")
  public void testEnhancedForLoop() {
    String results = "";
    for (String s: expectedAry) {
      results += " " + s;
    }
    results = results.stripLeading();
    String expected = String.join(" ", expectedAry);

    assertEquals(expected, results);
  }

  @Test
  @DisplayName("iterate over an array with a conditional")
  public void testIterateOverArrayWithConditional() {
    String results = "";
    for (int i = 0; i < expectedAry.length; i++) {
      if (i % 2 == 0) {
        results += " " + expectedAry[i];
      }
    }
    results = results.stripLeading();
    assertEquals("foo fizz", results);
  }

  @Test
  @DisplayName("conditionals")
  public void testConditionals() {
    String results = "";
    for (String s: expectedAry) {
      if (s.equals("foo")) {
        results += s;
      } else if (s.equals("bar")) {
        results += " " + s;
      } else if (s.equals("fizz")) {
        results += " " + s;
      } else {
        results += " " + s;
      }
    }
    String expected = String.join(" ", expectedAry);
    assertEquals(expected, results);
  }

  @Test
  @DisplayName("basics of ArrayList add to ArrayList")
  public void testBasicsOfArrayListAddToArrayList() {
    String[] expected = {"foo", "bar", "fizz", "buzz", "foobar", "fizzbuzz"};
    list.add("foobar");
    list.add("fizzbuzz");

    assertArrayEquals(expected, list.toArray());
  }

  @Test
  @DisplayName("basics of ArrayList remove from ArrayList")
  public void testBasicsOfArrayListRemoveFromArrayList() {
    String[] expected = {"foo", "bar", "fizz", "buzz", "fizzbuzz"};
    list.add("foobar");
    list.add("fizzbuzz");
    list.remove(4);

    assertArrayEquals(expected, list.toArray());
  }

  @Test
  @DisplayName("get and set an ArrayList")
  public void testGetAndSetAnArrayList() {
    String[] expected = {"bar", "fizzbuzz"};
    String[] results = new String[2];
    results[0] = list.get(1);
    list.set(1, "fizzbuzz");
    results[1] = list.get(1);

    assertArrayEquals(expected, results);
  }

  @Test
  @DisplayName("creating strings")
  public void testCreateStrings() {
    // == compares object references, equals compares content
    String s1 = "foobar";
    String s2 = "foobar";
    String s3 = new String("foobar");
    String s4 = new String("foobar");
    boolean[] expected = {true, true, false, true, false, true};
    boolean[] results = {s1 == s2, s1.equals(s2), s2 == s3, s2.equals(s3), s3 == s4, s3.equals(s4)};

    assertArrayEquals(expected, results);
  }
}