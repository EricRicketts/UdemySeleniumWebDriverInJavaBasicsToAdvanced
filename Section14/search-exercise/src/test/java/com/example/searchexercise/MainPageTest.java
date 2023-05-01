package com.example.searchexercise;

import org.example.SetWebDriverLocation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;
    private String searchTerm;

    @BeforeClass
    public static void oneTimeSetup() {
        SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }

    @BeforeMethod
    public void setUp() {
        searchTerm = "Rice";
        int implicitWaitTime = 5;
        int explicitWaitTime = 10;
        Duration duration = Duration.ofSeconds(explicitWaitTime);
        String url = "https://rahulshettyacademy.com/seleniumPractise/#/offers";
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);

        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, duration);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSearch() {
        List<WebElement> searchTermResults = new ArrayList<>();
        // assert that the page has loaded
        WebElement fruitOrVegetableHeading = wait.until(
                ExpectedConditions.visibilityOf(mainPage.fruitOrVegetableHeading)
        );
        Assert.assertNotNull(fruitOrVegetableHeading);

        // search for an item and validate the search
        // first get all the vegetable or fruit items in the table
        // and then search for the item
        mainPage.fruitOrVegetableNames.forEach(fruitOrVegetableName -> {
            if (fruitOrVegetableName.getText().contains(searchTerm)) {
                searchTermResults.add(fruitOrVegetableName);
            }
        });

        // there should be only one item and its text should match the
        // search term
        Assert.assertEquals(1, searchTermResults.size());
        Assert.assertEquals("Rice", searchTermResults.get(0).getText());
    }
}
