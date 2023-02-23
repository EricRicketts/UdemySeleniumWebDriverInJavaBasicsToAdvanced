package com.example.uipracticeexercise;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private final String url = "https://rahulshettyacademy.com/angularpractice/";
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;

  @BeforeAll
  public static void oneTimeSetup() {
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
  public void testUIExercise() throws InterruptedException {
    final String name = "Eric Ricketts";
    final String email = "eric_ricketts@iclould.com";
    final String password = "foo123bar";
    final String selectedGender = "Male";
    final String dateOfBirth = "11/20/1960";

    // input and assert on the name entry
    mainPage.nameInput.sendKeys(name);
    assertEquals(name, mainPage.nameInput.getAttribute("value"));

    // input and assert on the email entry
    mainPage.emailInput.sendKeys(email);
    assertEquals(email, mainPage.emailInput.getAttribute("value"));

    // input and assert on the password entry
    mainPage.passwordInput.sendKeys(password);
    assertEquals(password, mainPage.passwordInput.getAttribute("value"));

    // check and assert on the love ice cream checkbox
    assertFalse(mainPage.loveIceCreamCheckbox.isSelected());
    mainPage.loveIceCreamCheckbox.click();
    assertTrue(mainPage.loveIceCreamCheckbox.isSelected());

    // select the gender option and assert on it
    Select selectGender = new Select(mainPage.selectGender);
    selectGender.selectByIndex(0);
    assertEquals(selectedGender, selectGender.getAllSelectedOptions().get(0).getText());
  }
}
