package com.example.identifyelementwithidandnamelocators;

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

  private final String webDriversFolderPC = "C:\\Program Files\\WebDrivers\\";
  private final String chromeDriverWindows = "chromedriver.exe";
  private final String chromeDriverProperty = "webdriver.chrome.driver";
  private final String url = "https://rahulshettyacademy.com/locatorspractice/";
  private WebDriver driver;
  private MainPage mainPage;

  private String expectedUsername, expectedPassword;

  private WebElement inputForUserName, inputForPassword, signInButton;
  private Duration duration;

  private void setDriverLocationAndDriverSystemProperty() {
    String os = System.getProperty("os.name");
  }
  @BeforeEach
  public void setUp() {
    String os = System.getProperty("os.name");
    duration = Duration.ofSeconds(10);
    System.setProperty(chromeDriverProperty, webDriversFolderPC + chromeDriverWindows);
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(url);

    mainPage = new MainPage(driver);
    expectedUsername = "EricRicketts";
    expectedPassword = "foo123bar@#$";
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  @DisplayName("fill out input user name and password and submit using Selenium locators only")
  public void testFillOutUsernameAndPasswordAndSubmitWithLocators() {
    inputForUserName = driver.findElement(By.id("inputUsername"));
    inputForPassword = driver.findElement(By.name("inputPassword"));
    signInButton = driver.findElement(By.cssSelector("button.submit.signInBtn"));

    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(expectedPassword);

    assertEquals(expectedUsername, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    signInButton.click();

    WebDriverWait wait = new WebDriverWait(driver, duration);
    WebElement errorParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.errorParagraph));

    assertEquals("* Incorrect username or password", errorParagraph.getText());
  }

  @Test
  @DisplayName("fill out input user name and password an submit using the defined annotations")
  public void testFillOutInputUsernameAndPasswordAndSubmitUsingAnnotations() {
    inputForUserName = mainPage.inputUserName;
    inputForPassword = mainPage.inputPassword;
    signInButton = mainPage.signInButton;

    inputForUserName.sendKeys(expectedUsername);
    inputForPassword.sendKeys(expectedPassword);

    assertEquals(expectedUsername, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    signInButton.click();

    WebDriverWait wait = new WebDriverWait(driver, duration);
    WebElement errorParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.errorParagraph));

    assertEquals("* Incorrect username or password", errorParagraph.getText());
  }
}
