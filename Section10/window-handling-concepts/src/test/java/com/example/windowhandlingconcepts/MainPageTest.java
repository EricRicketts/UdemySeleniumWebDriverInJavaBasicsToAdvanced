package com.example.windowhandlingconcepts;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

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
    final String parentUrl = "https://rahulshettyacademy.com/loginpagePractise/";
    final int implicitWaitTime = 5;
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(parentUrl);

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testSwitchingBetweenWindows() {
    int explicitWaitTime = 10;
    Duration duration =  Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    WebElement blinkingTextLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.blinkingTextLink)
    );
    Assertions.assertNotNull(blinkingTextLink);

    blinkingTextLink.click();

    Set<String> allWindows = driver.getWindowHandles();
    Iterator<String> windowIDs = allWindows.iterator();
    String parentID = windowIDs.next();
    String childID = windowIDs.next();
    driver.switchTo().window(childID);

    WebElement desiredUserNameParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.desiredUsernameParagraph)
    );
    Assertions.assertNotNull(desiredUserNameParagraph);
  }
}
