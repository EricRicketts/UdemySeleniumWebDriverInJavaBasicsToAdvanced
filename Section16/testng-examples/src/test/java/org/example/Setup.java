package org.example;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class Setup {

    @BeforeTest
    public void beforeTestSetup() {
        Assert.assertEquals(1, 1);
        System.out.println("before test run");
/*
    we can see here that when running the package testng xml file the String "Before test run" will be
    printed at the console because, this xml file will run all tests listed in the package.  If we run
    testng_standard_run.xml this Setup file will not be run because that xml file specifically lists all
    tests to be run, since it does not specifically include setUp @BeforeTest will not be run.  @BeforeTest
    runs before ANY <test></test> tags.
*/
    }
}
