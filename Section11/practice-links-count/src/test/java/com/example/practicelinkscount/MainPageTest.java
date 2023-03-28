package com.example.practicelinkscount;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

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
    List<String> childWindowIDs = new ArrayList<>();
    List<String> expectedTitles = Arrays.asList(
      "REST API Tutorial",
      "The World's Most Popular API Testing Tool | SoapUI",
      "Appium Mobile Automation Testing from Scratch + Frameworks Tutorial | Udemy",
       "Apache JMeter - Apache JMeter™"
    );
    List<String> resultantTitles = new ArrayList<>();
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

    // get all the links in the first column then get all the links not part of a header element
    List<WebElement> firstColumnLinks = mainPage.firstColumnOfFooterTable.findElements(By.tagName("a"));
    List<WebElement> firstColumnNonHeaderLines = firstColumnLinks.subList(1, numberOfLinksInFirstFooterColumn);

    // for all the non-header links click each one is such a manner as to open up a window tab
    firstColumnNonHeaderLines.forEach(link -> {
      // ensures a new tab is opened in response to the click on the link
      String clickOnLinkTab = Keys.chord(Keys.COMMAND, Keys.ENTER);
      link.sendKeys(clickOnLinkTab);
    });

    // wait until all the tabs are open
    wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfLinksInFirstFooterColumn));

    // now instantiate the window handles, so we can move between each window
    Set<String> windows = driver.getWindowHandles();
    Iterator<String> windowIDs = windows.iterator();
    // get the parent window because we do not want to iterate over this one
    // just the child windows
    windowIDs.next();

    // iterate through the remaining window ids which are all child windows
    while (windowIDs.hasNext()) childWindowIDs.add(windowIDs.next());

    // move through the list of window ids and get the title for each window
    childWindowIDs.forEach(childID -> resultantTitles.add(driver.switchTo().window(childID).getTitle()));

    // in order to assert the lists are the same ensure the lists are the same size and each
    // list fully contains the other list
    boolean sameListsButNotInSameOrder = expectedTitles.size() == resultantTitles.size() &&
        expectedTitles.containsAll(resultantTitles) && resultantTitles.containsAll(expectedTitles);
    Assertions.assertTrue(sameListsButNotInSameOrder);
  }
}
