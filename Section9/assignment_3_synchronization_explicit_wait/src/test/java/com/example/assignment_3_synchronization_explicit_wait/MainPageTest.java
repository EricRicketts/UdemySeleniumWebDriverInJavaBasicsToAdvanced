package com.example.assignment_3_synchronization_explicit_wait;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  @BeforeAll
  public static void oneTimeSetup() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }
  @BeforeEach
  public void setUp() {
    // https://groups.google.com/g/chromedriver-users/c/xL5-13_qGaA?pli=1
    // above is url discussing the issue with ChromeDriver 111, when you
    // get to the website use "Expand All" to see all the conversation text in one page
    // Hopefully, this will work for both Windows and Mac.  Put whatever changes in the @BeforeAll
    // annotation as the webdriver should only have to be modified one time.  Also try to look
    // at the WebDriverManager code and figure out what it is doing.
    int implicitWaitTime = 5;
    String url = "https://rahulshettyacademy.com/loginpagePractise/";
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);

    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testSignInAndPurchaseItems() {
    // set the duration time for an explicit wait and then
    // get the WebDriverWait instance and initialize other
    // variables
    int explicitWaitTime = 10;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    String username = "rahulshettyacademy";
    String password = "learning";

    // to ensure the page has loaded wait for the sign-in
    // button to appear
    WebElement signInButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.signInButton)
    );
    Assertions.assertNotNull(signInButton);

    // enter username, password, check the User radio button
    mainPage.usernameInput.sendKeys(username);
    mainPage.passwordInput.sendKeys(password);
    mainPage.userRadioButton.click();

    // when the modal shows up regarding the user type
    // click the okay button, I thought the implicit wait
    // would work here being a modal I had to
    // wait for it to appear then click it
    WebElement okayButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.okayButton)
    );
    Assertions.assertNotNull(okayButton);
    okayButton.click();

    // in working with a select element in Selenium create
    // an instance of the Select class, then select the
    // "Consultant" option
    Select selectUserType = new Select(mainPage.userTypeSelect);
    selectUserType.selectByValue("consult");

    // accept the terms and conditions then select the sign-in button
    mainPage.termsAndConditionsCheckbox.click();
    mainPage.signInButton.click();

    // wait for the checkout button to appear on the next webpage
    WebElement checkoutButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.checkoutButton)
    );

    Assertions.assertNotNull(checkoutButton);

    // cycle through all the add to cart buttons and store the count
    mainPage.addToCartButtons.forEach(WebElement::click);
    String numberOfAddToCartItems = Integer.toString(mainPage.addToCartButtons.size());

    // assert on the number of items for checkout then go to checkout page
    Assertions.assertTrue(mainPage.checkoutButton.getText().contains(numberOfAddToCartItems));
    mainPage.checkoutButton.click();

    // wait for the final checkout button to be visible
    WebElement finalCheckoutButton = wait.until(
        ExpectedConditions.visibilityOf(mainPage.finalCheckoutButton)
    );
    Assertions.assertNotNull(finalCheckoutButton);
  }
}
