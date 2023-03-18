package com.example.fluentwaitexample;
import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeAll
    public static void oneTimeSetup() {
      System.setProperty("webdriver.http.factory", "jdk-http-client");
      SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }
    @BeforeEach
    public void setUp() {
//      String url = "https://the-internet.herokuapp.com/dynamic_loading";
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
    public void fluentWaitExample() throws InterruptedException {
      int explicitWaitTime = 10;
      Duration duration = Duration.ofSeconds(explicitWaitTime);
      WebDriverWait wait = new WebDriverWait(driver, duration);

      WebElement dynamicLoadingLink = wait.until(
          ExpectedConditions.visibilityOf(mainPage.dynamicLoadingLink)
      );
      dynamicLoadingLink.click();

      WebElement pageFooter = wait.until(
          ExpectedConditions.visibilityOf(mainPage.pageFooter)
      );
      Assertions.assertNotNull(pageFooter);
    }
}
