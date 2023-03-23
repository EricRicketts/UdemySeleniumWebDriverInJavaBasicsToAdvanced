package com.example.assignment4windowhandling;

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
    final int implicitWaitTime = 5;
    final String url = "https://the-internet.herokuapp.com/";
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
  public void testAssignmentFour() {
    String expectedChildWindowText = "New Window";
    String expectedParentWindowText = "Opening a new window";
    final int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // on the main page find the Multiple Windows link and click it
    WebElement multipleWindowsLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.multipleWindowsLink)
    );
    multipleWindowsLink.click();

    // on the Multiple Windows Page find the "Click Here" link to
    // get to the child window, then click the link
    WebElement childWindowLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.childWindowLink)
    );

    childWindowLink.click();


    // get the windows handles object
    Set<String> windows = driver.getWindowHandles();

    // get the iterator object so the correct window id
    // can be obtained
    Iterator<String> windowIDs = windows.iterator();
    String parentID = windowIDs.next();
    String childID = windowIDs.next();

    // switch to the child window id
    // though we clicked on the link we still have to tell
    // the driver to switch windows
    driver.switchTo().window(childID);

    // grab the text element in the child window
    boolean childWindowH3ElementFound = wait.until(
        ExpectedConditions.textToBePresentInElement(mainPage.windowH3Element, expectedChildWindowText)
    );
    Assertions.assertTrue(childWindowH3ElementFound);

    // now that there are two windows tell the driver to switch
    // to the parent window
    driver.switchTo().window(parentID);
    // get the text in the parent window
    boolean parentWindowH3ElementFound = wait.until(
        ExpectedConditions.textToBePresentInElement(mainPage.windowH3Element, expectedParentWindowText)
    );
    Assertions.assertTrue(parentWindowH3ElementFound);
  }
}
