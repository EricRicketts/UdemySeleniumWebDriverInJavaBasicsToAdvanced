package com.example.identifyelementwithidandnamelocators;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
  @DisplayName("search for the name input field")
  public void testSearchForNameInputField() {

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
