package com.example.parentsiblinglocators;

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
  String practiceButtonText = "Practice";
  String loginButtonText = "Login";
  String signUpButtonText = "Signup";
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;

  @BeforeEach
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get("https://www.rahulshettyacademy.com/AutomationPractice/");

    mainPage = new MainPage(driver);
    duration = Duration.ofSeconds(10);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testParentToChild() {

    // traversing parent to child
    String[] expected = new String[]{practiceButtonText, practiceButtonText};
    String[] results = new String[]{mainPage.practiceButtonWithXpath.getText(),
        mainPage.practiceButtonWithCss.getText()};

    assertArrayEquals(expected, results);

    expected = new String[]{loginButtonText, loginButtonText};
    results = new String[]{mainPage.loginButtonWithXpathSibling.getText(),
        mainPage.loginButtonWithCssSibling.getText()};

    assertArrayEquals(expected, results);

    // traverse child to parent
    expected = new String[]{"jumbotron", "text-center", "header_style"};
    results = mainPage.headerParentStartingFromPracticeButton.getAttribute("class").split("\s+");

    assertArrayEquals(expected, results);

    // traverse up and down parent child hierarchy using xpath
    assertEquals(signUpButtonText, mainPage.signUpButtonGoingUpAndDownElementHierarchy.getText());
  }

  @Test
  public void testNavigatingForwardAndBackwards() {
    driver.navigate().to("https://google.com");
    WebElement gmail = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Gmail']"))
    );
    assertEquals("Gmail", gmail.getText());
    driver.navigate().back();

    WebElement practiceButton = wait.until(
        ExpectedConditions.elementToBeClickable(mainPage.practiceButtonWithXpath)
    );

    assertEquals(practiceButtonText, practiceButton.getText());

    driver.navigate().forward();

    WebElement googleImage = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Google']"))
    );

    assertTrue(googleImage.isDisplayed());
  }
}