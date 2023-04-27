package org.example;

import java.util.ArrayList;
import java.util.List;

public class App {
    // this method takes a list of Integers and returns
    // a sorted list of unique numbers
    public List<Integer> findUniqueIntegers(List<Integer> list) {
        List<Integer> uniqueIntegerList =
                list.stream().distinct().sorted().toList();
        return uniqueIntegerList;
    }
}
