package com.example.assignment6varioustasks;

import org.example.SetWebDriverLocation;
import org.junit.jupiter.api.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPageTest {
  private WebDriver driver;
  private MainPage mainPage;

  @BeforeAll
  public static void oneTimeSetup() {
    System.setProperty("webdriver.http.factory", "jdk-http-client");
    SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
  }

  @BeforeEach
  public void setUp() {
    int implicitWaitTime = 5;
    final String url = "https://rahulshettyacademy.com/AutomationPractice/";
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
  public void testAssignmentSix() {
    /*
      1.  select one of the checkboxes under "Checkbox Example" and then grab the text associated with the checkbox
      2.  Navigate to the pull-down on the left and select the option you chose for your checkbox selection
      3.  Navigate to the "Switch To Alert Example" and enter your chosen option text then select the "Alert" button.
      4.  Verify the Alert message contains the chosen option
    */
    List<Integer> indicesList = new ArrayList<>();
    final int explicitWaitTime = 10;
    WebElement checkedCheckbox = null;
    int index = 0;
    Duration duration = Duration.ofSeconds(explicitWaitTime);
    WebDriverWait wait = new WebDriverWait(driver, duration);

    // ensure page is loaded by checking if the footer is visible
    WebElement footerDivContainerElement = wait.until(
        ExpectedConditions.visibilityOf(mainPage.footerDivContainerElement)
    );
    Assertions.assertNotNull(footerDivContainerElement);

    // get all the label elements of the checkboxes
    List<WebElement> checkboxLabelElements = wait.until(
        ExpectedConditions.visibilityOfAllElements(mainPage.checkboxLabelElements)
    );

    // build up the list of indices
    for (int listIndex = 0; listIndex < checkboxLabelElements.size(); listIndex++) indicesList.add(listIndex);

    // find the number of labels, then the random number will be between 0 and 1 less
    // the number of labels which matches the indices in the list
    int maxIndex= checkboxLabelElements.size() - 1;
    int minIndex = 0;
    int chosenLabelElementIndex = new Random().nextInt(maxIndex - minIndex) + minIndex;

    // assert the chosen index is contained in the list of indices
    Assertions.assertTrue(indicesList.contains(chosenLabelElementIndex));

    // use the randomly generated index to get the desired label, then get the text and click
    // on the checkbox
    WebElement chosenCheckboxLabelElement = checkboxLabelElements.get(chosenLabelElementIndex);
    String chosenCheckboxLabelText = chosenCheckboxLabelElement.getText();
    chosenCheckboxLabelElement.findElement(By.tagName("input")).click();

    // cycle through the checkboxes and get the checked element
    while (index < mainPage.checkboxInputElements.size()) {
      if (mainPage.checkboxInputElements.get(index).isSelected()) {
        checkedCheckbox = mainPage.checkboxInputElements.get(index);
        break;
      }
      index += 1;
    }

    // now get the parent of the checked checkbox and the text of the parent
    // it should match the chosen checkbox label
    Assertions.assertNotNull(checkedCheckbox);
    WebElement checkedCheckboxParentLabelElement = checkedCheckbox.findElement(By.xpath("parent::*"));
    String checkedCheckboxParentLabelElementText = checkedCheckboxParentLabelElement.getText();

    Assertions.assertEquals(chosenCheckboxLabelText, checkedCheckboxParentLabelElementText);

    // move to the select element and select the option with the same text as that
    // chosen via the checkbox elements
    Select dropdown = new Select(mainPage.dropdownElement);
    dropdown.selectByVisibleText(chosenCheckboxLabelText);
    WebElement selectedOption = dropdown.getFirstSelectedOption();

    Assertions.assertEquals(chosenCheckboxLabelText, selectedOption.getText());

    // navigate to the input element with id "name" and enter the checkbox selection
    // then select the alert button to get the pop-up
    mainPage.nameInputElement.sendKeys(chosenCheckboxLabelText);

    mainPage.alertButton.click();

    // switch to the alert dialog
    Alert alertDialog = driver.switchTo().alert();
    // grab the dialog text
    String alertText = alertDialog.getText();

    Assertions.assertTrue(alertText.contains(chosenCheckboxLabelText));
    alertDialog.accept();

    driver.switchTo().defaultContent();
  }
}
