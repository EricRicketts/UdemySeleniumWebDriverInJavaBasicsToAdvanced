package com.example.staticdropdowns;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  private String[] expected, results;

  @BeforeEach
  public void setUp() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void currencyDropdown() throws InterruptedException {
    String staticSelectId = "ctl00_mainContent_DropDownListCurrency";
    String staticSelectName = "ctl00$mainContent$DropDownListCurrency";
    duration = Duration.ofSeconds(10);
    wait = new WebDriverWait(driver, duration);
    WebElement staticDropdown = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id(staticSelectId))
    );

    expected = new String[]{staticSelectId, staticSelectName};
    results = new String[]{staticDropdown.getAttribute("id"), staticDropdown.getAttribute("name")};

    assertArrayEquals(expected, results);
    Select dropdown = new Select(staticDropdown);

    expected = new String[]{"", "INR", "AED", "USD"};
    List<String> resultsList = new ArrayList<>();
    dropdown.getOptions().forEach(webElement -> {
      resultsList.add(webElement.getAttribute("value"));
    });

    assertArrayEquals(expected, resultsList.toArray());
  }
}
