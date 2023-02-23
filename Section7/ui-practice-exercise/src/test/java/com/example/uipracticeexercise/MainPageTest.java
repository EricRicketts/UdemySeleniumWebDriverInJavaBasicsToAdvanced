package com.example.uipracticeexercise;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    final String studentRadioButtonLabelText = "Student";
    final String dateOfBirthKeys = "11201960";
    final String dateOfBirthValue = "1960-11-20";
    final String alertSuccessText = "Success!";

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
    boolean loveIceCreamCheckboxChecked = wait.until(
        ExpectedConditions.elementToBeSelected(mainPage.loveIceCreamCheckbox)
    );
    assertTrue(loveIceCreamCheckboxChecked);

    // select the gender option and assert on it
    Select selectGender = new Select(mainPage.selectGender);
    selectGender.selectByIndex(0);

    // wait until the selected option appears in the select field
    boolean genderSelected = wait.until(
        ExpectedConditions.textToBePresentInElement(mainPage.selectGender, selectedGender)
    );
    assertTrue(genderSelected);
    assertEquals(selectedGender, selectGender.getAllSelectedOptions().get(0).getText());

    // selected the employment status and assert on it

    // first make sure all the radio buttons on are not selected
    for (WebElement radioButton:mainPage.employmentStatusRadioButtons) {
      assertFalse(radioButton.isSelected());
    }

    // click the first radio button which is "Student" and assert that was chosen
    mainPage.employmentStatusRadioButtons.get(0).click();
    WebElement studentRadioButton = mainPage.employmentStatusRadioButtons.get(0);

    // wait until Selenium assures radio button is selected
    boolean studentRadioButtonSelected = wait.until(
        ExpectedConditions.elementToBeSelected(studentRadioButton)
    );

    // if button is selected then get the label
    assertTrue(studentRadioButtonSelected);

    // we get the id of the radio button itself and this will be the same
    // as the "for" attribute of the radio button label
    String studentRadioButtonId = studentRadioButton.getAttribute("id");
    String studentRadioButtonLabelXpath = "//label[@for='" + studentRadioButtonId + "']";
    WebElement studentRadioButtonLabel = driver.findElement(By.xpath(studentRadioButtonLabelXpath));
    assertEquals(studentRadioButtonLabelText, studentRadioButtonLabel.getText());

    //  nothing fancy here just set the date of birth and check on it
    mainPage.dateOfBirthInput.click();
    mainPage.dateOfBirthInput.sendKeys(dateOfBirthKeys);
    assertEquals(dateOfBirthValue, mainPage.dateOfBirthInput.getAttribute("value"));

    mainPage.submitButton.click();

    WebElement successAlert = wait.until(
        ExpectedConditions.visibilityOf(mainPage.successAlert)
    );

    assertTrue(successAlert.getText().contains(alertSuccessText));
  }
}
