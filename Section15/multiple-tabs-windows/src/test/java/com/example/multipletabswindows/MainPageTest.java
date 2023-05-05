package com.example.multipletabswindows;

import org.example.SetWebDriverLocation;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.testng.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private WebDriverWait wait;

  @BeforeClass
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeMethod
  public void setUp() {
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    String url = "https://www.rahulshettyacademy.com/angularpractice/";
    Duration duration = Duration.ofSeconds(explicitWaitTime);
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

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testMultipleWindowsAndTabs() throws InterruptedException {
    /*
      In this test we navigate to Rahul Shetty's angular page and are required
      to enter the first course name of his courses offered page which is at
      another url.  This will require making tabs and navigating to another url
      to grab the data and then navigate back to the original url to enter the
      data.
    */
    String secondUrl = "https://rahulshettyacademy.com/";
    // wait for the footer element to appear
    WebElement mainPageFooter = wait.until(
        ExpectedConditions.visibilityOf(mainPage.mainPageFooter)
    );
    Assert.assertNotNull(mainPageFooter);

    // create a new tab and navigate to the desired url
    // open a blank tab
    driver.switchTo().newWindow(WindowType.TAB);

    // get all the existing window handles then chose the new tab window handle
    List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());
    String originalWindowHandle = windowHandles.get(0);
    String newTabHandle = windowHandles.get(1);
    driver.switchTo().window(newTabHandle);
    // load the desired url
    driver.get(secondUrl);

    // verify landing on the new tab with its url
    WebElement newTabFooter = wait.until(
        ExpectedConditions.visibilityOf(mainPage.newTabFooter)
    );
    Assert.assertNotNull(newTabFooter);

    // get the text for the first course
    Assert.assertNotNull(mainPage.firstCourseLink);
    String firstCourseTextDescription = mainPage.firstCourseLink.getText();

    driver.switchTo().window(originalWindowHandle);
//    Thread.sleep(2000);
  }
}
