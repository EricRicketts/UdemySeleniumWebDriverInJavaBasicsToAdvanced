package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import model.DataDemonstration;

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
  @DisplayName("arrays can be initialized to a size")
  public void testArraysInitializedToASize() {
    int[] expected = {1, 2, 3, 4, 5};
    int[] result = new int[5]; // initialize array to a size of 5 values
    // iterate over the array with an enhanced for loop
    for (int index : new int[]{0, 1, 2, 3, 4}) {
      result[index] = index + 1;
    }
    assertArrayEquals(expected, result);
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
    // enhanced for loop iterating over an array of Strings
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
  @DisplayName("size of arraylist")
  public void testSizeOfArrayList() {
    ArrayList<String> results = new ArrayList<>();
    String[] expected = { "ab foo", "cd bar", "ef fizz", "gh buzz"};
    String[] prefixes = {"ab", "cd", "ef", "gh"};
    for (int index = 0; index < list.size(); index += 1) {
        results.add(prefixes[index] + " " + list.get(index));
    }
    assertArrayEquals(expected, results.toArray());
  }

  @Test
  @DisplayName("ArrayList contains method")
  public void testArrayListContains() {
    assertTrue(list.contains("buzz"));
  }

  @Test
  @DisplayName("ArrayList convert Array to List")
  public void testConvertArrayToList() {
    String[] resultArray = {"foo", "bar", "fizz", "buzz"};
    List<String> resultList = Arrays.asList(resultArray);
    assertArrayEquals(list.toArray(), resultList.toArray());
  }

  @Test
  @DisplayName("creating strings")
  public void testCreateStrings() {
    // == compares object references, equals compares content
    // in the case of the String literal declaration below, only
    // one string is allocated in memory.
    String s1 = "foobar";
    String s2 = "foobar";

    // in the case of creating a new String object, two different objects
    // are created so two memory allocations are created
    String s3 = new String("foobar");
    String s4 = new String("foobar");
    boolean[] expected = {true, true, false, true, false, true};
    boolean[] results = {s1 == s2, s1.equals(s2), s2 == s3, s2.equals(s3), s3 == s4, s3.equals(s4)};
    assertArrayEquals(expected, results);
  }

  @Test
  @DisplayName("common string methods")
  public void testCommonStringMethods() {
    String s = "foo bar fizz buzz";
    assertArrayEquals(expectedAry, s.split("\\s+"));
    s = " foobar ";
    assertEquals("foobar ", s.stripLeading());
    assertEquals(" foobar", s.stripTrailing());
    assertEquals("foobar", s.trim());
    assertEquals('b', s.charAt(4));
  }

  @Test
  @DisplayName("reverse a string")
  public void testReverseAString() {
    String a = "abcdef";
    String expected = "fedcba";
    String result = "";
    for (int index = a.length() - 1; index > -1; index -= 1) {
      result += a.charAt(index);
    }
    assertEquals(expected, result);
  }

  @Test
  @DisplayName("instance and class methods")
  public void testInstanceAndClassMethods() {
    DataDemonstration dd = new DataDemonstration();
    Object[] expected = {0, 1, "Hello", "Goodbye"};
    Object[] results = new Object[4];
    results[0] = dd.getData();
    dd.setData(1);
    results[1] = dd.getData();
    results[2] = dd.sayHello();
    results[3] = DataDemonstration.sayGoodBye();
    assertArrayEquals(expected, results);
  }
}