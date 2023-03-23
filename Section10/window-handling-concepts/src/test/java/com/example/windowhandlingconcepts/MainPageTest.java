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

    // this is the link we need to select to bring up the next window tab
    WebElement blinkingTextLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.blinkingTextLink)
    );
    Assertions.assertNotNull(blinkingTextLink);

    blinkingTextLink.click();

    // we now have more than one window a parent and a child window
    Set<String> allWindows = driver.getWindowHandles();

    // We need to get an iterator object to traverse the Set
    Iterator<String> windowIDs = allWindows.iterator();

    // iteration starts outside the Set, so when we do the first
    // next() call we arrive at the parent id
    String parentID = windowIDs.next();
    // the second next() call takes us to the child window id
    String childID = windowIDs.next();
    // use the id to tell the driver to switch to the child window
    driver.switchTo().window(childID);

    // get the desired paragraph so we can grab the username
    WebElement desiredUserNameAnchor = wait.until(
        ExpectedConditions.visibilityOf(mainPage.desiredUsernameAnchor)
    );
    Assertions.assertNotNull(desiredUserNameAnchor);

    // get the text from the anchor element of the child page
    // we need to set the text here because once we switch
    // back to the parent window we will loose this element
    String usernameEntry = desiredUserNameAnchor.getText();
    // get the parent paragraph to run those a parsing exercise
    String usernameParagraphText = mainPage.getDesiredUsernameParagraph.getText();

    // get the desired username entry text by splitting at the word "at" trimming the white space
    // then splitting on a space and taking the first element
    String desiredUsername = usernameParagraphText.split("at")[1].trim().split(" ")[0];
    Assertions.assertEquals(usernameEntry ,desiredUsername);

    // switch back to the parent
    driver.switchTo().window(parentID);

    // get the username input element
    WebElement usernameInput = wait.until(
        ExpectedConditions.visibilityOf(mainPage.usernameInput)
    );


    // enter the text into the username input element and assert it has been entered
    usernameInput.sendKeys(usernameEntry);
    Assertions.assertEquals(usernameEntry, usernameInput.getAttribute("value"));
  }
}
