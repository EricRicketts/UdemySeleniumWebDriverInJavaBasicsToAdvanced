package org.example;

import java.util.Arrays;

public class App {
  public static void main(String[] args) {
    int intr = 5;
    double dbl = 5.89;
    boolean bol = true;
    Object[] output = {intr, dbl, bol};
    String str = "Foo Bars";

    System.out.println(Arrays.toString(output));
    System.out.println(intr + " " + str);
  }
}
