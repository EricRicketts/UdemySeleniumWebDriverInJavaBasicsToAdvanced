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

        // derive the expected results using streams
        List<WebElement> searchTermResultsUsingStreams =
            mainPage.fruitOrVegetableNames.stream()
                .filter(e -> e.getText().contains(searchTerm)).toList();

        // there should be only one item and its text should match the
        // search term
        Assert.assertEquals(1, searchTermResults.size());
        Assert.assertEquals("Rice", searchTermResults.get(0).getText());

        // assert on the stream results
        Assert.assertEquals(1, searchTermResultsUsingStreams.size());
        Assert.assertEquals("Rice", searchTermResultsUsingStreams.get(0).getText());

        // use the search functionality and see if the table reduces to one element
        mainPage.searchField.sendKeys(searchTerm);
        List<WebElement> searchedElements = wait.until(
            ExpectedConditions.numberOfElementsToBe(
                By.xpath("//tbody/tr/td[1]"), 1
            )
        );

        // what Rahul Shetty did AFTER using the search bar
        // was to first filter the list which remained after the search
        // was completed using the search bar

        // then what he did after that was to grab all the elements
        // in the list that remained AFTER using the search bar
        // this lists should be the same

        // here we are using streams to filter the list AFTER the
        // search was completed
        List<WebElement> searchTermResultsUsingStreamsFileredList =
            mainPage.fruitOrVegetableNames.stream()
                .filter(e -> e.getText().contains(searchTerm)).toList();

        // get the list as a whole AFTER the search was completed
        List<WebElement> tableAfterSearch = mainPage.fruitOrVegetableNames;

        // assert that the two lists are the same
        Assert.assertEquals(searchTermResultsUsingStreamsFileredList, tableAfterSearch);

        // assert size and content of table search
        Assert.assertEquals(1, searchedElements.size());
        String tableSearchResults = searchedElements
            .stream().map(e -> e.getText()).toList().get(0);
        Assert.assertEquals(searchTerm, tableSearchResults);

        // after looking at the Rahul Shetty video a better test
        // is to cycle through the results of the search list and
        // validate each list item contains the search term.  This
        // is a much more generic solution
        List<String> tableSearchResultsTextList = searchedElements
            .stream().map(e -> e.getText()).toList();
        tableSearchResultsTextList.stream()
            .forEach(str -> Assert.assertEquals(searchTerm, str));
    }
}
