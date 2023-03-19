package com.example.fluentwaitexample;
import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class MainPageTest {
    private final int explicitWaitTime = 10;
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeAll
    public static void oneTimeSetup() {
      System.setProperty("webdriver.http.factory", "jdk-http-client");
      SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }
    @BeforeEach
    public void setUp() {
      String url = "https://the-internet.herokuapp.com";
      int implicitWaitTime = 5;
      driver = new ChromeDriver();
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
      driver.get(url);

      mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testFluentWaitExample() {
      int pollingTime = 2;
      Duration duration = Duration.ofSeconds(explicitWaitTime);
      Duration pollingDuration = Duration.ofSeconds(pollingTime);
      WebDriverWait wait = new WebDriverWait(driver, duration);

      // go to the main page and then select the Dynamic Loading link
      WebElement dynamicLoadingLink = wait.until(
          ExpectedConditions.visibilityOf(mainPage.dynamicLoadingLink)
      );
      dynamicLoadingLink.click();

      // once we are on the Dynamic Loading link page we should
      // see the page footer
      WebElement pageFooter = wait.until(
          ExpectedConditions.visibilityOf(mainPage.pageFooter)
      );
      Assertions.assertNotNull(pageFooter);

      // now to get on the correct page to run our test select
      // the hidden element on page link
      WebElement elementHiddenOnPageLink = wait.until(
          ExpectedConditions.visibilityOf(mainPage.elementHiddenOnPageLink)
      );
      elementHiddenOnPageLink.click();

      // the start button will begin our exercise
      WebElement startButton = wait.until(
          ExpectedConditions.visibilityOf(mainPage.startButton)
      );
      Assertions.assertNotNull(startButton);

      startButton.click();

      // define the fluent wait set its entire duration time (10 seconds)
      // and its polling time (2 seconds) which are the intervals in which
      // selenium will look for the element
      Wait<WebDriver> fluentWait = new FluentWait<>(driver)
          .withTimeout(duration)
          .pollingEvery(pollingDuration)
          .ignoring(NoSuchElementException.class);

      // if we do not define this condition the test will fail, we have to tell
      // the fluent wait that wait until the element is displayed on the page before
      // returning it.  We need to do it in this case because the element is already
      // on the html for the page but it is just not visible until the loading element
      // is no longer visible
      WebElement fluentWaitResult = fluentWait.until(
          webDriver -> mainPage.fluentWaitResult.isDisplayed() ? mainPage.fluentWaitResult : null
      );
      Assertions.assertTrue(fluentWaitResult.getText().contains("Hello World!"));
    }

    @Test
    public void testNoFluentWaitExample() {
      Duration duration = Duration.ofSeconds(explicitWaitTime);
      WebDriverWait wait = new WebDriverWait(driver, duration);

      // go to the main page and then select the Dynamic Loading link
      WebElement dynamicLoadingLink = wait.until(
          ExpectedConditions.visibilityOf(mainPage.dynamicLoadingLink)
      );
      dynamicLoadingLink.click();

      // once we are on the Dynamic Loading link page we should
      // see the page footer
      WebElement pageFooter = wait.until(
          ExpectedConditions.visibilityOf(mainPage.pageFooter)
      );
      Assertions.assertNotNull(pageFooter);

      // now to get on the correct page to run our test select
      // the hidden element on page link
      WebElement elementHiddenOnPageLink = wait.until(
          ExpectedConditions.visibilityOf(mainPage.elementHiddenOnPageLink)
      );
      elementHiddenOnPageLink.click();

      // the start button will begin our exercise
      WebElement startButton = wait.until(
          ExpectedConditions.visibilityOf(mainPage.startButton)
      );
      Assertions.assertNotNull(startButton);

      startButton.click();

      // this is one way to work around using a fluent wait
      // the developer can wait until the element is no longer present
      // in the case of clicking the start button a loading GIF appears
      // and in the code below, a wait is defined which returns a boolean
      // true once the loading gif disappears from view
      boolean loadingGIFFinished = wait.until(
          ExpectedConditions.invisibilityOf(mainPage.loadingGIF)
      );

      // not necessary but done to get rid of the warning of variable
      // not used
      Assertions.assertTrue(loadingGIFFinished);

      // wait for the "Hello World!" h4 element to show
      WebElement fluentWaitResult = wait.until(
          ExpectedConditions.visibilityOf(mainPage.fluentWaitResult)
      );

      Assertions.assertTrue(fluentWaitResult.getText().contains("Hello World!"));
    }
}
