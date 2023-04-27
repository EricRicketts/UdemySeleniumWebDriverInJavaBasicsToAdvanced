package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;


public class AppTest {

    @Test
    public void testApp() {
        List<Integer> testList = Arrays.asList(3, 2, 2, 7, 5, 1, 9, 7);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 7, 9);
        List<Integer> results = new App().findUniqueIntegers(testList);

        Assertions.assertEquals(expected, results);
    }
}
