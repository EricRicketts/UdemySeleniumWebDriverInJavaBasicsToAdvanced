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

  @BeforeEach
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get("https://www.rahulshettyacademy.com/AutomationPractice/");

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testParentToChild() {
    Duration duration = Duration.ofSeconds(10);
    WebDriverWait wait = new WebDriverWait(driver, duration);

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
}