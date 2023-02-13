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
import org.example.SetWebDriverLocation;

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
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
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
    // get the button labeled with text 'Practice Button' two ways, one with xpath and another with css
    String[] expected = new String[]{practiceButtonText, practiceButtonText};
    String[] results = new String[]{mainPage.practiceButtonWithXpath.getText(),
        mainPage.practiceButtonWithCss.getText()};

    assertArrayEquals(expected, results);

    // traversing parent to child
    // get the button labeled with 'Login' two ways, xpath siblings and css siblings
    expected = new String[]{loginButtonText, loginButtonText};
    results = new String[]{mainPage.loginButtonWithXpathSibling.getText(),
        mainPage.loginButtonWithCssSibling.getText()};

    assertArrayEquals(expected, results);

    // traverse child to parent using xpath to arrive at the header element from the button labeled 'Practice'
    // assert on the classes contained in the header element
    expected = new String[]{"jumbotron", "text-center", "header_style"};
    results = mainPage.headerParentStartingFromPracticeButton.getAttribute("class").split("\s+");

    assertArrayEquals(expected, results);

    // traverse up and down parent child hierarchy using xpath
    // start with the header go down to the button labeled 'Practice' back up to the parent div element
    // and then back down to the first button which is labeled 'Signup'
    assertEquals(signUpButtonText, mainPage.signUpButtonGoingUpAndDownElementHierarchy.getText());
  }

  @Test
  public void testNavigatingForwardAndBackwards() {
    // in this case we are telling the browser to navigate backwards then forwards
    // just to show that these methods are available in Selenium

    // we loaded https://www.rahulshettyacademy.com/AutomationPractice/ in the setUp method
    // now we navigate to google.com and then assert we are there by asserting on the text of the
    // gmail link
    driver.navigate().to("https://google.com");
    WebElement gmail = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Gmail']"))
    );
    assertEquals("Gmail", gmail.getText());

    // navigate back to the Automation Practice site and assert on the button with text 'Practice'
    driver.navigate().back();

    WebElement practiceButton = wait.until(
        ExpectedConditions.elementToBeClickable(mainPage.practiceButtonWithXpath)
    );

    assertEquals(practiceButtonText, practiceButton.getText());

    // navigate forward to google site and assert we are on the web page by finding and asserting
    // on the well known google image
    driver.navigate().forward();

    WebElement googleImage = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Google']"))
    );

    assertTrue(googleImage.isDisplayed());
  }
}