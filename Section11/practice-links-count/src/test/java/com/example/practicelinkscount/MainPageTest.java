package com.example.practicelinkscount;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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
    final String url = "https://rahulshettyacademy.com/AutomationPractice/";
    final int implicitWaitTime = 5;
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
  public void testNumberOfLinks() {
    final int expectedNumberOfLinks = 27;
    final int expectedNumberOfLinksInFooter = 20;
    final int expectedNumberOfLinksInFirstFooterColumn = 5;
    final int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // ensure the page is loaded before running any locators
    // in this case we are making sure the bottom of the page
    // the copyright text is visible
    WebElement copyrightLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.copyrightLink)
    );
    Assertions.assertNotNull(copyrightLink);

    // get all the links on the page and assert on the number of links
    int numberOfLinks = mainPage.allLinkElements.size();
    Assertions.assertEquals(expectedNumberOfLinks, numberOfLinks);

    // get the number of links in the footer section and assert on their number
    int numberOfLinksInFooter = mainPage.footerDivElement.findElements(By.tagName("a")).size();
    Assertions.assertEquals(expectedNumberOfLinksInFooter, numberOfLinksInFooter);

    // get the links count in the first footer section only
    int numberOfLinksInFirstFooterColumn = mainPage.firstColumnOfFooterTable.findElements(By.tagName("a")).size();
    Assertions.assertEquals(expectedNumberOfLinksInFirstFooterColumn, numberOfLinksInFirstFooterColumn);

    // how click on each link in the first footer section and verify you have landed on the appropriate page
    List<WebElement> firstColumnLinks = mainPage.firstColumnOfFooterTable.findElements(By.tagName("a"));
  }
}
