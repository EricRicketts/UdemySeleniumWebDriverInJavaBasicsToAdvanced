package com.example.introjavastreams;

import org.assertj.core.api.SoftAssertions;
import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private WebDriverWait wait;
  private SoftAssertions softAssertions;
  private List<String> names = Arrays.asList(new String[]{
      "Alpha", "Beta", "Charlie", "Delta", "Abby", "Echo", "Foxtrot",
      "Golf", "Alternate", "Amoral", "Hotel", "India", "Juliet"
    }
  );

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    ChromeOptions options = new ChromeOptions();
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);
    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
    softAssertions = new SoftAssertions();
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testStreamsPartOne() {
    int expectedNumberOfNamesStartingWithA = 4;
    // scroll to the footer element
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].scrollIntoView(true)", mainPage.footerElement);

    // assert the footer element is in the viewport
    boolean footerIsInView = wait.until(
        ExpectedConditionUtils.isVisibleInViewport(mainPage.footerElement)
    );
    softAssertions.assertThat(footerIsInView).isTrue();

    List<String> namesStartingWithA =
        names.stream().filter(name -> name.startsWith("A")).toList();

    softAssertions.assertThat(namesStartingWithA.size())
        .isEqualTo(expectedNumberOfNamesStartingWithA);

    // make all assertions
    softAssertions.assertAll();
  }
}
