package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class AppTest {

    @Test
    public void testUniqueAndSorted() {
        List<Integer> testList = Arrays.asList(3, 2, 2, 7, 5, 1, 9, 7);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 7, 9);
        List<Integer> results = new App().findUniqueIntegersAndSort(testList);

        Assertions.assertEquals(expected, results);
    }

    @Test
    public void testUniqueAndReverseSorted() {
        List<Integer> testList = Arrays.asList(3, 2, 2, 7, 5, 1, 9, 7);
        List<Integer> expected = Arrays.asList(9, 7, 5, 3, 2, 1);
        List<Integer> results = new App().findUniqueIntegersAndReverseSort(testList);

        Assertions.assertEquals(expected, results);
    }

    @Test
    public void testGetElementFromUniqueAndSorted() {
        List<Integer> testList = Arrays.asList(3, 2, 2, 7, 5, 1, 9, 7);
        int expected = 3;
        int results = new App().findUniqueIntegersAndSort(testList).get(2);

        Assertions.assertEquals(expected, results);
    }
}
