package com.example.assignment7;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  Duration duration;
  WebDriverWait wait;

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    duration = Duration.ofSeconds(explicitWaitTime);
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
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
  public void testAssignmentSeven() {
    int expectedCourseTableRows = 11;
    int expectedCourseTableHeaders = 3;

    // manually count rows and columns for expected
    // get the actual results from the WebElements
    int[] expected = new int[]{expectedCourseTableRows, expectedCourseTableHeaders};
    int[] result = new int[]{mainPage.courseTableRows.size(), mainPage.courseTableColumns.size()};
    String[] expectedSecondRow = new String[]{
        "Rahul Shetty",
        "Learn SQL in Practical + Database Testing from Scratch",
        "25"};
    List<String> resultsSecondRow = new ArrayList<>();

    // scroll until the table is in view then assert it is in view
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.courseTable);
    boolean courseTableInView =
        wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.courseTable));
    Assertions.assertTrue(courseTableInView);

    // cycle through the data elements <td> and grab the text for each
    for (int index = 0; index < mainPage.secondTableRowData.size(); index++) {
      String rowData = mainPage.secondTableRowData.get(index).getText();
      resultsSecondRow.add(rowData);
    }

    // assert on the expected second row data
    Assertions.assertArrayEquals(expectedSecondRow, resultsSecondRow.toArray());
    // assert on the actual number of course table rows and columns
    Assertions.assertArrayEquals(expected, result);
  }
}
