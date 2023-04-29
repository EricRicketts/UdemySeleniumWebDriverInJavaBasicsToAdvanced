package com.example.getprices;

import org.assertj.core.api.IntegerAssert;
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
import java.util.*;

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
        // initialize the expected values
        List<String> expectedFruitOrVegetableNames =
                new ArrayList<>(Arrays.asList("Wheat", "Tomato", "Strawberry", "Rice", "Potato"));
        List<Integer> expectedFruitOrVegetablePrices =
                new ArrayList<>(Arrays.asList(67, 37, 23, 37, 34));
        HashMap<String, Integer> expectedNameAndPriceData = new HashMap<>();
        expectedNameAndPriceData.put("Wheat", 67);
        expectedNameAndPriceData.put("Tomato", 37);
        expectedNameAndPriceData.put("Strawberry", 23);
        expectedNameAndPriceData.put("Rice", 37);
        expectedNameAndPriceData.put("Potato", 34);

        // wait until the table heading for the fruit or vegetable name is in view
        // then assert it was in view
        boolean fruitOrVegetableNameColumnHeadingIsVisible = wait.until(
                ExpectedConditions.textToBePresentInElement(
                        mainPage.fruitOrVegetableNameColumnHeading, "Veg/fruit name")
        );
        softAssertions.assertThat(fruitOrVegetableNameColumnHeadingIsVisible).isTrue();

        // use streams to get all fruit or vegetable names and assert they are correct
        List<String> fruitOrVegetableNames =
                mainPage.fruitOrVegetableNameElements.stream().map(e -> e.getText()).toList();
        softAssertions.assertThat(fruitOrVegetableNames).isEqualTo(expectedFruitOrVegetableNames);

        // use streams to get all fruit or vegetable prices and assert they are correct
        List<Integer> fruitOrVegetablePrices =
                mainPage.fruitOrVegetablePriceElements.stream().map(e -> Integer.parseInt(e.getText())).toList();
        softAssertions.assertThat(fruitOrVegetablePrices).isEqualTo(expectedFruitOrVegetablePrices);

        // cycle through each of the rows and get the fruit or vegetable name and its corresponding price
        mainPage.fruitOrVegetableTableRows.forEach(row -> {
            String fruitOrVegetableName = row.findElement(By.cssSelector("td:first-child")).getText();
            String fruitOrVegetablePriceText = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
            int fruitOrVegetablePrice = Integer.parseInt(fruitOrVegetablePriceText);

            fruitOrVegetableNamesAndPrices.put(fruitOrVegetableName, fruitOrVegetablePrice);
        });

        // assert the correct values were obtained from the table
        softAssertions.assertThat(fruitOrVegetableNamesAndPrices)
                .containsExactlyEntriesOf(expectedNameAndPriceData);
        // assert on the price of Rice
        softAssertions.assertThat(fruitOrVegetableNamesAndPrices.get("Rice")).isEqualTo(37);

        // finally get the price of Strawberry using streams
        // filter to find Strawberry, then get the following sibling which contains the price
        String strawberryPriceText = mainPage.fruitOrVegetableNameElements.stream()
                .filter(e -> e.getText().contains("Strawberry"))
                .map(e -> e.findElement(By.xpath("//following-sibling::td[1]")))
                .toList().get(0).getText();
        int strawberryPrice = Integer.parseInt(strawberryPriceText);
        softAssertions.assertThat(strawberryPrice).isEqualTo(23);
    }
}