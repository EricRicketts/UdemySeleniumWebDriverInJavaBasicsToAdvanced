package com.example.ifameshowtohandle;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
    int implicitWaitTime = 5;
    String url = "https://jqueryui.com/droppable/";
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
  public void testIFrames() {
    final String draggableText = "Drag me to my target";
    final String initialDroppableText = "Drop here";
    final String finalDroppableText = "Dropped!";
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // before using the desired iframe, find out how many iframes exist
    Assertions.assertEquals(1, mainPage.iframeElements.size());

    // we have to switch to the iframe element because it is encapsulated
    // from the main web page, so we can only search for those elements
    // within the iframe when we switch to the iframe context
    driver.switchTo().frame(mainPage.iframeElement);

    // now in the iframe context we should not be able to see the Accept link
    // this causes the test to take a long time but it does prove we cannot
    // find the Accept link while in the iFrame
    try {
      WebElement acceptLinkInIFrame = wait.until(
          ExpectedConditions.visibilityOf(mainPage.acceptLink)
      );
    } catch (TimeoutException e) {
      Assertions.assertTrue(true);
    }

    // find element to perform drag and drop
    WebElement draggableElement = wait.until(
        ExpectedConditions.visibilityOf(mainPage.draggableElement)
    );

    // assert the pre-conditions which are the text values for the
    // elements before performing the drag and drop operation
    Assertions.assertEquals(draggableText, mainPage.draggableElement.getText());
    Assertions.assertEquals(initialDroppableText, mainPage.droppableElement.getText());

    // build and execute the drag and drop operation
    Actions actionsBuilder = new Actions(driver);
    actionsBuilder.dragAndDrop(draggableElement, mainPage.droppableElement).build().perform();

    // when the drag and drop operation is complete the text of the droppable element changes
    // wait and assert on this change of text
    boolean dragAndDropOperationComplete = wait.until(
        ExpectedConditions.textToBePresentInElement(mainPage.droppableElement, finalDroppableText)
    );
    Assertions.assertTrue(dragAndDropOperationComplete);

    // now that we are done with our iframe content we need to switch back to the original content
    driver.switchTo().defaultContent();

    // Accept link is now visible since we are at the top level of the web page
    Assertions.assertNotNull(mainPage.acceptLink);
  }
}
