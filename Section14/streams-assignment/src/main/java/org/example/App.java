package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {
    // this method takes a list of Integers and returns
    // a sorted list of unique numbers
    public List<Integer> findUniqueIntegersAndSort(List<Integer> list) {
        List<Integer> uniqueIntegerListSorted =
                list.stream().distinct().sorted().toList();
        return uniqueIntegerListSorted;
    }
    public List<Integer> findUniqueIntegersAndReverseSort(List<Integer> list) {
        List<Integer> uniqueIntegerListReverseSorted =
                list.stream().distinct().sorted(Comparator.reverseOrder()).toList();
        return uniqueIntegerListReverseSorted;
    }
}
