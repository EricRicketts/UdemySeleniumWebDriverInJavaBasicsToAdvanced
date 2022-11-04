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
  private static final String url = "https://rahulshettyacademy.com/locatorspractice/";
  private WebDriver driver;
  private MainPage mainPage;

  @BeforeEach
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(url);

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  @DisplayName("fill out input user name and password an submit")
  public void testFillOutInputUsernameAndPasswordAndSubmit() {
    Duration duration = Duration.ofSeconds(10);
    String expectedUserName = "EricRicketts";
    String expectedPassword = "foo123bar@#$";

    WebElement inputForUserName = mainPage.inputUserName;
    WebElement inputForPassword = mainPage.inputPassword;
    WebElement signInButton = mainPage.signInButton;

    inputForUserName.sendKeys("EricRicketts");
    inputForPassword.sendKeys("foo123bar@#$");

    assertEquals(expectedUserName, inputForUserName.getAttribute("value"));
    assertEquals(expectedPassword, inputForPassword.getAttribute("value"));

    signInButton.click();

    WebDriverWait wait = new WebDriverWait(driver, duration);
    WebElement errorParagraph = wait.until(
        ExpectedConditions.visibilityOf(mainPage.errorParagraph));

    assertEquals("* Incorrect username or password", errorParagraph.getText());
  }
/*

  @Test
  public void search() {
    mainPage.searchButton.click();

    WebElement searchField = driver.findElement(By.cssSelector("[data-test='search-input']"));
    searchField.sendKeys("Selenium");

    WebElement submitButton = driver.findElement(By.cssSelector("button[data-test='full-search-button']"));
    submitButton.click();

    WebElement searchPageField = driver.findElement(By.cssSelector("input[data-test='search-input']"));
    assertEquals("Selenium", searchPageField.getAttribute("value"));
  }

  @Test
  public void toolsMenu() {
    mainPage.toolsMenu.click();

    WebElement menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"));
    assertTrue(menuPopup.isDisplayed());
  }

  @Test
  public void navigationToAllTools() {
    mainPage.seeAllToolsButton.click();

    WebElement productsList = driver.findElement(By.id("products-page"));
    assertTrue(productsList.isDisplayed());
    assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());
  }

*/
}
