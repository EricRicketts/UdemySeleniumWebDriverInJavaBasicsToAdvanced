package com.example.staticdropdowns;

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

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  @BeforeEach
  public void setUp() {
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

    String[] expected = new String[]{staticSelectId, staticSelectName};
    String[] results = new String[]{staticDropdown.getAttribute("id"), staticDropdown.getAttribute("name")};

    assertArrayEquals(expected, results);
    Select dropdown = new Select(staticDropdown);

    assertEquals(4, dropdown.getAllSelectedOptions().size());
  }
}
