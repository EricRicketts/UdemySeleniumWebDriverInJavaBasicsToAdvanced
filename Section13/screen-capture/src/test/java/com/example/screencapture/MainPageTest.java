package com.example.screencapture;

import org.junit.jupiter.api.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.activation.MimetypesFileTypeMap;
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
    String screenCaptureFile = "/screenshot.png";
    String fullCapturePath = capturePath + screenCaptureFile;

    // scroll the course table into view before taking the screen shot
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.courseTable);

    boolean courseTableInView = wait.until(ExpectedConditionUtils.isVisibleInViewport(mainPage.courseTable));
    Assertions.assertTrue(courseTableInView);

    // setup and take the screenshot
    TakesScreenshot screenshot = (TakesScreenshot) driver;

    File src = screenshot.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(src, new File(fullCapturePath));

    File file = new File(fullCapturePath);
    String mimeType = new MimetypesFileTypeMap().getContentType(file);

    String[] fileTypes = mimeType.split("/");
    String generalType = fileTypes[0];
    String specificType = fileTypes[1];

    // we can now assert it is some kind of image file, that it is
    // specifically a png file and that it is not empty (file length > 0)

    Assertions.assertEquals("image", generalType);
    Assertions.assertEquals("png", specificType);
    Assertions.assertTrue(file.length() > 0);
    /*
      Rahul Shetty made a comment about storing the file in Windows you cannot just store it
      in the following manner, C:\\screenshot.png because Windows will flag permission issues.
      So in Windows store it in C:\\Users\\<directory name>\\screenshot.png
    */
  }
}
