package com.example.getprices;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    WebDriverWait wait;
    HashMap<String, Integer> fruitOrVegetableNamesAndPrices;
    SoftAssertions softAssertions;

    @BeforeEach
    public void setUp() {
        softAssertions = new SoftAssertions();
        fruitOrVegetableNamesAndPrices = new HashMap<>();
        int implicitWaitTime = 5;
        int explicitWaitTime = 10;
        Duration duration = Duration.ofSeconds(explicitWaitTime);
        String url = "https://rahulshettyacademy.com/seleniumPractise/#/offers";
        ChromeOptions options = new ChromeOptions();
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
    public void testGetPrices() {
        HashMap<String, Integer> expected = new HashMap<>();
        expected.put("Wheat", 67);
        expected.put("Tomato", 37);
        expected.put("Strawberry", 23);
        expected.put("Rice", 37);
        expected.put("Potato", 34);

        // wait until the table heading for the fruit or vegetable name is in view
        // then assert it was in view
        boolean fruitOrVegetableNameColumnHeadingIsVisible = wait.until(
                ExpectedConditions.textToBePresentInElement(
                        mainPage.fruitOrVegetableNameColumnHeading, "Veg/fruit name")
        );
        softAssertions.assertThat(fruitOrVegetableNameColumnHeadingIsVisible).isTrue();

        // cycle through each of the rows and get the fruit or vegetable name and its corresponding price
        mainPage.fruitOrVegetableTableRows.forEach(row -> {
            String fruitOrVegetableName = row.findElement(By.cssSelector("td:first-child")).getText();
            String fruitOrVegetablePriceText = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
            int fruitOrVegetablePrice = Integer.parseInt(fruitOrVegetablePriceText);

            fruitOrVegetableNamesAndPrices.put(fruitOrVegetableName, fruitOrVegetablePrice);
        });

        softAssertions.assertThat(fruitOrVegetableNamesAndPrices).containsExactlyEntriesOf(expected);
    }
}