package com.example.tablesortingusingstreams;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        int implicitWaitTime = 5;
        int explicitWaitTime = 10;
        Duration duration = Duration.ofSeconds(explicitWaitTime);
        String url = "https://rahulshettyacademy.com/seleniumPractise/#/";
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
        driver.get(url);

        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, duration);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testTableSorting() {
        List<String> fruitOrVegetableNames = new ArrayList<>();
        // scroll the vegetable into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", mainPage.vegetableH4);

        WebElement desiredVegetable = wait.until(
                ExpectedConditions.visibilityOf(mainPage.vegetableH4)
        );
        // assert it is in view
        Assertions.assertNotNull(desiredVegetable);

        // click the Top Deals link
        mainPage.topDealsLink.click();

        // wait until there are two windows
        boolean twoWindowsArePresent =
                wait.until(numberOfWindowsToBe(2));

        Assertions.assertTrue(twoWindowsArePresent);

        // put the window handles into a list for access
        List<String> windowHandlesList = new ArrayList<>(driver.getWindowHandles());

        // switch to the cart window
        String cartWindowHandle = windowHandlesList.get(1);
        driver.switchTo().window(cartWindowHandle);

        // ensure the table is in view
        WebElement tableHeadingVegetableOrFruitName = wait.until(
                ExpectedConditions.visibilityOf(mainPage.tableHeadingVegetableOrFruitName)
        );

        Assertions.assertNotNull(tableHeadingVegetableOrFruitName);

        // click the table heading to sort the list of fruits or vegetables
        mainPage.tableHeadingVegetableOrFruitName.click();

        // get the current list of fruit or vegetable names
        for (WebElement fruitOrVegetableName : mainPage.fruitOrVegetableNames) {
            fruitOrVegetableNames.add(fruitOrVegetableName.getText());
        }

        // switch to using assertJ library
        assertThat(fruitOrVegetableNames.size() > 0).isTrue();

        // take the current list of fruits and vegetables and sort them
        List<String> sortedFruitOrVegetableNames =
                new ArrayList<>(fruitOrVegetableNames.stream().sorted().toList());

        // now the sorted list and the list before sorting should be the same
        // as clicking the heading should have sorted the element
        assertThat(fruitOrVegetableNames).isEqualTo(sortedFruitOrVegetableNames);
    }
}
