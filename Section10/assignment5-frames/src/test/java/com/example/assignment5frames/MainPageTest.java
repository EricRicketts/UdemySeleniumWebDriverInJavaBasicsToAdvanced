package com.example.assignment5frames;

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
  public void testAssignmentFiveIFrames() {
    final String expectedText = "MIDDLE";
    final int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // wait for the nested frames link to appear then click on it
    WebElement nestedFramesLink = wait.until(
        ExpectedConditions.visibilityOf(mainPage.nestedFramesLink)
    );
    nestedFramesLink.click();

    // arriving at the page we should detect the top level frameset
    WebElement topLevelFrameset = wait.until(
        ExpectedConditions.visibilityOf(mainPage.topLevelFrameset)
    );
    Assertions.assertNotNull(topLevelFrameset);

    // driver.switchTo().frame(topLevelFrameset);
    // this is no need to explicitly switch to this frameset
    // because once we land on the page we are already in that frameset

    // this is the top frame of the top and bottom frames, this top frame
    // contains left, middle and right frames.  The middle frame is the
    // ultimate target, as noted below frameset and frame are not supported
    // in HTML5, Selenium will not allow me to switch to a frameset but it
    // will allow me to switch between frames.  All future development should
    // use the iframe element, which is supported by HTML5, additionally it is
    // fine to nest iframes
    driver.switchTo().frame(mainPage.topFrameElementWithinTopLevelFrameset);

    // this is not the top level frameset which we started with, but this
    // frameset contains the left, middle and right frames.
    // one very important node frameset and frame are not supported in HTML5
    // everything should be iframe, this test uses frameset and frame so this might
    // be why I cannot explicitly switch to the frameset in Selenium
    // I can find the frameset using either of the commands below
    // WebElement middleFrameset = driver.findElement(By.cssSelector("[name$='frameset-middle']"));
    // WebElement middleFrameset = mainPage.middleFrameset
    // but I cannot switch to the frameset => driver.switchTo().frame(middleFrameset);
    // driver.switchTo().frame(framesetList.get(0)); in both cases I get the following error:
    // org.openqa.selenium.NoSuchFrameException: no such frame
    // my guess is that though I can find the tag frameset by a selenium element search, Selenium
    // does not recognize frameset as a valid frame, so it is saying to me, "the element you want to
    // switch to is not a valid frame

    // we find the embedded frameset and assert we found it
    List<WebElement> framesetList = driver.findElements(By.cssSelector("frameset[name$='frameset-middle']"));
    Assertions.assertEquals(1, framesetList.size());
    Assertions.assertNotNull(framesetList.get(0));

    // now that we are in the frameset that contains the left, middle and right frames
    // switch to the middle frame
    driver.switchTo().frame(mainPage.middleFrameElement);

    // now that we are in the middle frame search for the element which contains
    // the desired text
    Assertions.assertEquals(expectedText, driver.findElement(By.id("content")).getText());
  }
}
