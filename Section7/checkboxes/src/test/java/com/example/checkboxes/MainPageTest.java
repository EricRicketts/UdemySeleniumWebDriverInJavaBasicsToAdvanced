package com.example.checkboxes;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  private String url = "https://rahulshettyacademy.com/dropdownsPractise/";

  private Duration duration;
  private WebDriverWait wait;

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(url);

    mainPage = new MainPage(driver);
    duration = Duration.ofSeconds(10);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testCheckboxes() throws InterruptedException {
    // in this method we are finding the checkboxes individually
    // by different locator methods, see MainPage.java to examine the
    // different locator techniques used in the @FindBy annotations
    WebElement friendsAndFamilyCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.friendsAndFamilyCheckbox)
    );

    WebElement seniorCitizenCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.seniorCitizenCheckbox)
    );

    WebElement indianArmedServicesCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.indianArmedServicesCheckbox)
    );

    WebElement studentCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.studentCheckbox)
    );

    WebElement unaccompaniedMinorCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.unaccompaniedMinorCheckbox)
    );

    friendsAndFamilyCheckbox.click();
    assertTrue(friendsAndFamilyCheckbox.isSelected());

    seniorCitizenCheckbox.click();
    assertTrue(seniorCitizenCheckbox.isSelected());

    indianArmedServicesCheckbox.click();
    assertTrue(indianArmedServicesCheckbox.isSelected());

    studentCheckbox.click();
    assertTrue(studentCheckbox.isSelected());

    unaccompaniedMinorCheckbox.click();
    assertTrue(unaccompaniedMinorCheckbox.isSelected());
  }
}
