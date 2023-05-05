package com.example.relativelocators;

import org.example.SetWebDriverLocation;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.locators.RelativeLocator.*;
import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private WebDriverWait wait;

  @BeforeClass
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeMethod
  public void setUp() {
    String url = "https://www.rahulshettyacademy.com/angularpractice/";
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);

    mainPage = new MainPage(driver);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testRelativeLocatorsAboveAndBelow() {
    // wait until the footer is visible, which is at the
    // bottom of the page
    WebElement footer = wait.until(
        ExpectedConditions.visibilityOf(mainPage.footer)
    );
    Assert.assertNotNull(footer);

    // make sure the input element can be found
    Assert.assertNotNull(mainPage.nameInput);

    // get the label for the name element using relative locators
    // note for some reason my pre-defined elements in my MainPage class do not work
    // with the with static method => driver.findElement(with(By.tagName("label")).above(mainPage.nameInput));
    WebElement nameInput = driver.findElement(By.cssSelector("input.form-control[name$='name']"));
    Assert.assertNotNull(nameInput);
    WebElement nameLabel = driver.findElement(with(By.tagName("label")).above(nameInput));
    Assert.assertEquals(nameLabel.getText(), "Name");

    // make sure the date of birth label can be found
    Assert.assertNotNull(mainPage.dateOfBirthLabel);

    // find the date of birth input using below
    WebElement dateOfBirthLabel = driver.findElement(By.xpath("//label[@for='dateofBirth']"));
    WebElement dateOfBirthInput = driver.findElement(with(By.tagName("input"))
        .below(dateOfBirthLabel));
    Assert.assertNotNull(dateOfBirthInput);


  }

  @Test
  public void testRelativeLocatorsRightAndLeft() {
    // wait until the footer is visible, which is at the
    // bottom of the page
    WebElement footer = wait.until(
        ExpectedConditions.visibilityOf(mainPage.footer)
    );
    Assert.assertNotNull(footer);

    // find the checkbox by its label and then check it
    // find the checkbox by using the left of locator
    String checkboxLabelLocator = "label[for='exampleCheck1']";
    WebElement checkboxLabel = driver.findElement(By.cssSelector(checkboxLabelLocator));
    WebElement checkbox = driver.findElement(with(By.tagName("input")).toLeftOf(checkboxLabel));
    Assert.assertNotNull(checkbox);

    // check the checkbox
    checkbox.click();
    Assert.assertTrue(checkbox.isSelected());

    // find the first radio button label and assert on its value
    // find the first radio button
    WebElement studentRadioButton = driver.findElement(By.id("inlineRadio1"));
    // now use the radio button to get its label and assert on the text content of the label
    WebElement studentRadioButtonLabel =
        driver.findElement(with(By.tagName("label")).toRightOf(studentRadioButton));
    Assert.assertEquals("Student", studentRadioButtonLabel.getText());

  }
}
