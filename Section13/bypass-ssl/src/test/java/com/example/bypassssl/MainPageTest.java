package com.example.bypassssl;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  private ChromeOptions options;

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    String url = "https://expired.badssl.com/";
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    duration = Duration.ofSeconds(explicitWaitTime);
    options = new ChromeOptions();
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*");
    options.setAcceptInsecureCerts(true);

    // options must be passed at the time of the browser invocation
    // so set all of your options beforehand
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testByPassSSL() throws InterruptedException {
    String expectedTitle = "expired.badssl.com";

    // wait until the <h1> element appears and assert
    // the resulting WebElement is not null
    WebElement badSSLText = wait.until(
        ExpectedConditions.visibilityOf(mainPage.badSSLText)
    );
    Assertions.assertNotNull(badSSLText);

    // assert on the title of the bad ssl page
    Assertions.assertEquals(expectedTitle, driver.getTitle());

  }
}
/*
  https://chromedriver.chromium.org/capabilities
  Rahul Shetty had a video on setting up a lot of different capabilities of the ChromeDriver
  before the browser is actually invoked.  The above page is what he referenced, some of
  his highlights: setting up a proxy, block pop up windows, and setting the download directory
  note DesiredCapabilities has been deprecated, it is only used by Ruby and Python
*/
