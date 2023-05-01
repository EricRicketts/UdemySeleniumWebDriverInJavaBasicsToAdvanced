package com.example.paginationexample;

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
    private String searchItem;

    @BeforeClass
    public static void oneTimeSetup() {
        SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }

    @BeforeMethod
    public void setUp() {
        searchItem = "Wheat";
        int implicitWaitTime = 5;
        int explicitWaitTime = 10;
        Duration duration = Duration.ofSeconds(explicitWaitTime);
        String url = "https://rahulshettyacademy.com/seleniumPractise/#/offers";
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

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testPaginationWithStreams() {
        String expectedPrice = "67";
        List<WebElement> desiredFruitOrVegetableItem = new ArrayList<>();
        // fruit and vegetable heading visible and asserted on
        WebElement fruitOrVegetableHeading = wait.until(
                ExpectedConditions.visibilityOf(mainPage.fruitOrVegetableHeading)
        );
        Assert.assertNotNull(fruitOrVegetableHeading);

        // click the heading to alphabetize the entries
        fruitOrVegetableHeading.click();

        // wait for the list to sort and then find the first element
        boolean firstElementOfAlphabetizedListIsPresent = wait.until(
                ExpectedConditions.textToBePresentInElement(mainPage.firstElementOfAlphabetizedList, "Almond")
        );
        Assert.assertTrue(firstElementOfAlphabetizedListIsPresent);

        // now look for the price of the search item.  Iterate through the current table
        // if the item is found add it to the capture list, if it is not found click the next button
        do {
            for (WebElement fruitOrVegetableNameElement : mainPage.fruitOrVegetableNameElements) {
                if (fruitOrVegetableNameElement.getText().contains(searchItem)) {
                    desiredFruitOrVegetableItem.add(fruitOrVegetableNameElement);
                }
            }
            if (desiredFruitOrVegetableItem.size() < 1) mainPage.nextButton.click();
        } while (desiredFruitOrVegetableItem.size() < 1);

        // assert that we found the search item
        Assert.assertEquals(1, desiredFruitOrVegetableItem.size());

        // find the price of the search item and assert on it
        WebElement searchItemPriceElement = desiredFruitOrVegetableItem
                .get(0).findElement(By.xpath("following-sibling::td[1]"));
        Assert.assertEquals(expectedPrice, searchItemPriceElement.getText());
    }
}
