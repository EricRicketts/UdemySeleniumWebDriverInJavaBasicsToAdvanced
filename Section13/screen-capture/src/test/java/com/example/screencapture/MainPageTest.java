package com.example.screencapture;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  Duration duration;
  WebDriverWait wait;

  @BeforeEach
  public void setUp() {
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    duration = Duration.ofSeconds(explicitWaitTime);
    ChromeOptions options = new ChromeOptions();
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*");
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
  public void testTakeScreenShot() throws IOException {
    String capturePath = "/Users/ericricketts/Documents/UdemySeleniumWebDriverInJavaBasicsToAdvanced/" +
        "Section13/screen-capture/src/test/java/com/example/screencapture";
    TakesScreenshot screenshot = (TakesScreenshot) driver;

    File src = screenshot.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(src, new File(capturePath + "/screenshot.png"));
  }
}
