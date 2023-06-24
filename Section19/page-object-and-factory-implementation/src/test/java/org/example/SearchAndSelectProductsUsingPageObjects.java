package org.example;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class SearchAndSelectProductsUsingPageObjects {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        Duration duration;
        int implicitWaitTime = 10;
        int explicitWaitTime = 15;
    }
}
