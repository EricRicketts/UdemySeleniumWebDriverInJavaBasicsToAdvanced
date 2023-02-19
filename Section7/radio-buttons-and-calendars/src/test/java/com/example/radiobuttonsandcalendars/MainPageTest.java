package com.example.radiobuttonsandcalendars;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private String url = "https://rahulshettyacademy.com/dropdownsPractise/";
  private Duration duration;
  private WebDriverWait wait;

  @BeforeAll
  private static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    duration = Duration.ofSeconds(10);
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testOneWayTrip() {

  }
}
