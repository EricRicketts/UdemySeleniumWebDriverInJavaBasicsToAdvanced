package com.example.checkboxexercises;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  private String url = "https://rahulshettyacademy.com/AutomationPractice/";

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
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testCheckboxes() {
    // for each checkbox wait until each checkbox is visible before
    // acquiring the element
    WebElement firstCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.checkBoxOptionOne)
    );

    WebElement secondCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.checkBoxOptionTwo)
    );

    WebElement thirdCheckbox = wait.until(
        ExpectedConditions.visibilityOf(mainPage.checkBoxOptionThree)
    );

    // get all the checkboxes as a list
    List<WebElement> allCheckBoxes = wait.until(
        ExpectedConditions.visibilityOfAllElements(mainPage.allCheckBoxOptions)
    );

    // the checkboxes are input elements of type checkbox, create an array
    // of the expected value attributes, after this for the results array
    // get the value attribute from each checkbox
    String[] expected = new String[]{"option1", "option2", "option3"};
    String[] results = new String[]{
        firstCheckbox.getAttribute("value"),
        secondCheckbox.getAttribute("value"),
        thirdCheckbox.getAttribute("value")
    };

    // assert on the value attributes for each checkbox
    // then assert on the number of checkboxes in the page
    assertArrayEquals(expected, results);
    assertEquals(3, allCheckBoxes.size());

    // create an array of all the checkbox variables
    WebElement[] allNamedCheckBoxes = new WebElement[]{firstCheckbox, secondCheckbox, thirdCheckbox};

    // select each checkbox and assert that each is selected
    allCheckBoxes.forEach(checkbox -> checkbox.click());
    for (WebElement checkbox:allNamedCheckBoxes) {
      assertTrue(checkbox.isSelected());
    }

    // select each checkbox and assert that each is not selected
    allCheckBoxes.forEach(checkbox -> checkbox.click());
    for (WebElement checkbox:allNamedCheckBoxes) {
      assertFalse(checkbox.isSelected());
    }
  }
}
