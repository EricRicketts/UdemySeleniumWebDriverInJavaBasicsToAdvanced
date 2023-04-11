package com.example.scrollingwithjavascrptexecutor;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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
import java.util.Arrays;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  @BeforeAll
  public static void oneTimeSetup() {
      SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    int implicitTimeWait = 5;
    int explicitTimeWait = 10;
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitTimeWait));
    driver.get(url);

    mainPage = new MainPage(driver);
    duration = Duration.ofSeconds(explicitTimeWait);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testScrollExercise() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    int firstRowPositionTop, firstRowPositionLeft;
    int secondRowPositionTop, secondRowPositionLeft;
    int totalAmount = 0;
    String[] expectedFirstRowDataEntries = new String[]{"Alex", "Engineer", "Chennai", "28"};
    List<String> resultantFirstRowDataEntries = new ArrayList<>(){};

    // assert that the product table exists on the webpage before doing anything
    Assertions.assertNotNull(mainPage.productTable);

    // use the utility class to scroll the product table into view and assert it is in the viewport
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.productTable);
    boolean productTableInView = wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.productTable));
    Assertions.assertTrue(productTableInView);

    // for "dead reckoning scrolling js.executeScript("window.scrollBy(0, 500")
    // just scroll the desired amount

    // assert that list for all the product purchase amounts is not empty then
    // cycle through the data in the table and sum each amount
    Assertions.assertTrue(mainPage.productAmounts.size() > 0);
    for (int index = 0; index < mainPage.productAmounts.size(); index++) {
      String amountText = mainPage.productAmounts.get(index).getText();
      totalAmount += Integer.parseInt(amountText);
    }

    // verify alternative CSS matches original CSS
    Assertions.assertEquals(mainPage.productAmounts.size(), mainPage.alternativeProductAmounts.size());

    // go to the div element which contains the table total this should match what
    // is obtained by the loop above
    String totalDivElementText = mainPage.totalAmountElement.getText();

    // the text is "Total Amount Collected: 296" (or some number)
    // split on the ":" so the number remains in the last array position
    // since the number is going to have at least one space before it, invoke the trim method
    // to isolate the number
    String[] totalAmountEntireTextArray = totalDivElementText.split(":");
    String totalAmountNumberString = totalAmountEntireTextArray[totalAmountEntireTextArray.length - 1].trim();

    // assert the manual sum and the total amount displayed are the same
    Assertions.assertEquals(Integer.parseInt(totalAmountNumberString), totalAmount);

    // now for an exercise to manually scroll within the table
    // assert the first row content
    WebElement firstTableRow = mainPage.allProductRows.get(0);
    WebElement secondTableRow = mainPage.allProductRows.get(1);

    // get all the td elements within the first row and then get their content
    List<WebElement> firstTableRowDataElements = firstTableRow.findElements(By.tagName("td"));
    for (WebElement firstTableRowDataElement : firstTableRowDataElements) {
      String data = firstTableRowDataElement.getText();
      resultantFirstRowDataEntries.add(data);
    }

    // assert first row content versus expected content
    Assertions.assertNotNull(resultantFirstRowDataEntries);
    Assertions.assertArrayEquals(expectedFirstRowDataEntries, resultantFirstRowDataEntries.toArray());

    // get the position of the first element in the table, note this position is absolute
    // it is not relative to another element, say the parent element
    firstRowPositionTop = firstTableRow.getLocation().getY();
    firstRowPositionLeft = firstTableRow.getLocation().getX();
    secondRowPositionTop = secondTableRow.getLocation().getY();
    secondRowPositionLeft = secondTableRow.getLocation().getX();
    int scrollAmountAbsolute = secondRowPositionTop - firstRowPositionTop;

    // there should be no horizontal scrolling and the second element should be
    // lower than the first element
    Assertions.assertEquals(firstRowPositionLeft, secondRowPositionLeft);
    Assertions.assertTrue(secondRowPositionTop > firstRowPositionTop);
    // since no scrolling has taken place as of yet, the second row vertical position
    // should be the first row vertical position plus the vertical difference between
    // the first and second rows
    Assertions.assertEquals((firstRowPositionTop + scrollAmountAbsolute), secondRowPositionTop);

    // now we execute the scroll
    js.executeScript("document.querySelector('div.tableFixHead').scrollTop = arguments[0]",
            scrollAmountAbsolute);

    // this is the absolute position of the second row in the table so the element was scrolled
    // to the top of the table meaning its new position was that of the first table row's original
    // position we have to get the position again because I do not believe there is a live update
    // in the List of rows, so we cannot assert on secondRowPositionTop variable
    int scrolledSecondRowPositionTop = mainPage.secondProductRow.getLocation().getY();
    Assertions.assertEquals(firstRowPositionTop, scrolledSecondRowPositionTop);
  }

  @Test
  public void testSumOtherTable() {
    int expectedTotalPrice = 235;

    // scroll until the table is in view
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.coursePriceTable);
    boolean coursePriceTableInView =
        wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.coursePriceTable));
    Assertions.assertTrue(coursePriceTableInView);


    // sum up the course prices and assert against the expected value
    int coursesPriceTotal = 0;
    for (int index = 0; index < mainPage.coursePrices.size(); index++) {
      int coursePrice = Integer.parseInt(mainPage.coursePrices.get(index).getText().trim());
      coursesPriceTotal += coursePrice;
    }

    Assertions.assertEquals(expectedTotalPrice, coursesPriceTotal);
  }
}