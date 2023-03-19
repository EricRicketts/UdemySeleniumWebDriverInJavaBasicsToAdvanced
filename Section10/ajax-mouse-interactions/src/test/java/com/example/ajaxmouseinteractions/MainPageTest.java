package com.example.ajaxmouseinteractions;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    int implicitWaitTime = 10;
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get("https://www.jetbrains.com/");

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
  }
}
