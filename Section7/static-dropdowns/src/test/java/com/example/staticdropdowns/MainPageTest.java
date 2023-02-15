package com.example.staticdropdowns;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;
  private Duration duration;
  private WebDriverWait wait;
  private String[] expected, results;

  @BeforeEach
  public void setUp() {
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    driver = new ChromeDriver();
    driver.manage().window().maximize();
//    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
    mainPage = new MainPage(driver);
  }

  @AfterEach
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testCurrencyDropdown() throws InterruptedException {
    // this method will exercise a static dropdown element

    // here we get the id and name attribute of the static dropdown which
    // allows the user to select the currency for payment
    String staticSelectId = "ctl00_mainContent_DropDownListCurrency";
    String staticSelectName = "ctl00$mainContent$DropDownListCurrency";

    // for some reason I found when these were put in the BeforeEach
    // method I found these variables did not initialize as thought,
    // I might try again later
    duration = Duration.ofSeconds(10);
    wait = new WebDriverWait(driver, duration);

    // get the static dropdown by id
    WebElement staticDropdown = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id(staticSelectId))
    );

    // verify it is the correct static dropdown by asserting on id and name attributes
    expected = new String[]{staticSelectId, staticSelectName};
    results = new String[]{staticDropdown.getAttribute("id"), staticDropdown.getAttribute("name")};

    assertArrayEquals(expected, results);

    // convert the element to a Selenium Select instance which adds more
    // convenience methods
    Select dropdown = new Select(staticDropdown);

    expected = new String[]{"", "INR", "AED", "USD"};
    List<String> resultsList = new ArrayList<>();

    // getOptions produces a list of all <options> elements
    dropdown.getOptions().forEach(webElement -> {
      resultsList.add(webElement.getAttribute("value"));
    });

    assertArrayEquals(expected, resultsList.toArray());
    // tell the browser to select the 4th option, 0 based indexing
    dropdown.selectByIndex(3);

    // this method will either get the first option or return the currently
    // selected option
    assertEquals("USD", dropdown.getFirstSelectedOption().getText());

    // we can also select by the visible texts displayed in the dropdown
    // again remember this method as does selectByIndex() returns void because
    // they are going into the browser and exercising the select element
    dropdown.selectByVisibleText("AED");
    assertEquals("AED", dropdown.getFirstSelectedOption().getText());

    // the final way to select is by value, which makes sense as most option
    // elements have a value attribute
    dropdown.selectByValue("INR");
    assertEquals("INR", dropdown.getFirstSelectedOption().getText());
  }

  @Test
  public void testPassengersDropdown() throws InterruptedException {
    // this method tests a dropdown not made up of <select> and <option>
    // elements but of nested <div> elements.
    duration = Duration.ofSeconds(10);
    wait = new WebDriverWait(driver, duration);

    WebElement passengerDropdown = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("divpaxinfo"))
    );

    // we have to click the top level div to show the options then we can interact
    // with the options and either add or subtract passengers
    passengerDropdown.click();

    // get the "+" sign for adding adult passengers
    WebElement addAdultsPlusSign = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("hrefIncAdt"))
    );

    // click three times to add 3 more adult passengers bringing the total to 4
    for (int i = 0; i < 3; i+=1) addAdultsPlusSign.click();

    // find the done button to complete the process of adding passengers
    WebElement passengersDropdownDoneButton = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("btnclosepaxoption"))
    );
    passengersDropdownDoneButton.click();

    // wait for the done to clear
    passengerDropdown = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("divpaxinfo"))
    );

    assertEquals("4 Adult", passengerDropdown.getText());
  }
}
