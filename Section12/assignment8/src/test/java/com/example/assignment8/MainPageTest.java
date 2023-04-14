package com.example.assignment8;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPageTest {
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
    String url = "https://rahulshettyacademy.com/AutomationPractice/";
    int implicitWaitTime = 5;
    int explicitWaitTime = 10;
    ChromeOptions options = new ChromeOptions();
    // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
    options.addArguments("--remote-allow-origins=*");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
    driver.get(url);

    mainPage = new MainPage(driver);
    duration = Duration.ofSeconds(explicitWaitTime);
    wait = new WebDriverWait(driver, duration);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testDynamicDropdown() {
    // first ensure the dynamic dropdown is visible
    boolean dynamicInputIsVisible = wait.until(
        ExpectedConditionUtils.isVisibleInViewport(mainPage.inputForDynamicDropdown)
    );
    Assertions.assertTrue(dynamicInputIsVisible);

    // click in the dynamic dropdown and they type "rus"
    mainPage.inputForDynamicDropdown.click();
    mainPage.inputForDynamicDropdown.sendKeys("rus");

    // the entry into the input field triggers the pullDown
    // assert that it appears, it is a <ul>
    WebElement pullDownList = wait.until(
        ExpectedConditions.visibilityOf(mainPage.pulldownList)
    );
    Assertions.assertNotNull(pullDownList);

    // search the parent <ul> for the desired child and select it
    pullDownList.findElement(By.xpath("//div[contains(text(), 'Russian Federation')]")).click();

    // assert the input element has the desired selection
    boolean russianFederationIsSelected = wait.until(
        ExpectedConditions.attributeToBe(
            mainPage.inputForDynamicDropdown,
            "value",
            "Russian Federation")
    );
    Assertions.assertTrue(russianFederationIsSelected);
  }

  @Test
  public void testDynamicDropdownWithManualSelection() {
    // first ensure the dynamic dropdown is visible
    boolean dynamicInputIsVisible = wait.until(
        ExpectedConditionUtils.isVisibleInViewport(mainPage.inputForDynamicDropdown)
    );
    Assertions.assertTrue(dynamicInputIsVisible);

    // click in the dynamic dropdown and they type "rus"
    mainPage.inputForDynamicDropdown.click();
    mainPage.inputForDynamicDropdown.sendKeys("rus");

    // the entry into the input field triggers the pullDown
    // assert that it appears, it is a <ul>
    WebElement pullDownList = wait.until(
        ExpectedConditions.visibilityOf(mainPage.pulldownList)
    );
    Assertions.assertNotNull(pullDownList);

    for (int numberOfKeyDowns = 0; numberOfKeyDowns < 4; numberOfKeyDowns++) {
      if (numberOfKeyDowns == 3) {
        mainPage.inputForDynamicDropdown.sendKeys(Keys.DOWN, Keys.ENTER);
      } else {
        mainPage.inputForDynamicDropdown.sendKeys(Keys.DOWN);
      }
    }

    // assert the input element has the desired selection
    boolean russianFederationIsSelected = wait.until(
        ExpectedConditions.attributeToBe(
            mainPage.inputForDynamicDropdown,
            "value",
            "Russian Federation")
    );
    Assertions.assertTrue(russianFederationIsSelected);
  }
}
