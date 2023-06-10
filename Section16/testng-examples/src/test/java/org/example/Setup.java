package org.example;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class Setup {

    @BeforeTest
    public void beforeTestSetup() {
        Assert.assertEquals(1, 1);
        System.out.println("before all tests run");
    }

    @AfterTest
    public void afterTestCleanup() {
        Assert.assertEquals(2, 2);
        System.out.println("after all tests run");
    }

    @BeforeSuite
    public void beforeSuiteSetup() {
        Assert.assertEquals(3, 3);
        System.out.println("before the suite run");
    }

    @AfterSuite
    public void afterSuiteSetup() {
        Assert.assertEquals(4, 4);
        System.out.println("after the suite run");
    }

}

/*
    we can see here that when running the package testng xml file the String "Before test run" will be
    printed at the console because, this xml file will run all tests listed in the package.  If we run
    testng_standard_run.xml this Setup file will not be run because that xml file specifically lists all
    tests to be run, since it does not specifically include setUp @BeforeTest will not be run.  @BeforeTest
    runs before ANY <test></test> tags.

    Likewise the @AfterTest will run after all tests within the <test></test> tags have been run.

    @BeforeSuite and @AfterSuite follow the same pattern but they will proceed and follow the @BeforeTest
    and @AfterTest annotations as a suite takes precedence over a test, so we should see the console log
    of the suite first followed by the test log then the @AfterTest log followed by the @AfterSuite log.

    In fact this is exactly what we see logged out to the console.
*/
