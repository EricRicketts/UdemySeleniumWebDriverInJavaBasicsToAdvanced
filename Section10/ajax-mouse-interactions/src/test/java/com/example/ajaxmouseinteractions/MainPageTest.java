package com.example.ajaxmouseinteractions;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    final String url = "https://www.amazon.com";
    int implicitWaitTime = 10;
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
  public void testAjaxMouseInteractions() {
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);
    // The Actions instance provides us with more specific interaction
    // with the browser, like moving the mouse, selecting individual keys, etc.
    Actions actions = new Actions(driver);

    // first make sure the element we want to mouse over is visible
    // then move the mouse over the element that will lead to the sign in pop up
    WebElement accountAndListsElement = wait.until(
        ExpectedConditions.visibilityOf(mainPage.accountsAndListsElement)
    );
    Assertions.assertNotNull(accountAndListsElement);

    // the element is found now move to it
    actions.moveToElement(accountAndListsElement).build().perform();

    // wait for the pop-up to appear and then assert it is not null
    WebElement popUpSignInButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.popUpSignInButton)
    );
    Assertions.assertNotNull(popUpSignInButton);

    // click in the search bar, hold down the SHIFT key, type in "HELLO"
    actions.moveToElement(mainPage.searchInput)
        .click()
        .keyDown(Keys.SHIFT)
        .sendKeys("hello")
        .build()
        .perform();

    Assertions.assertTrue(mainPage.searchInput.getAttribute("value").contains("HELLO"));
  }
}
