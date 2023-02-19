package com.example.checkboxes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

// page_url = https://www.jetbrains.com/
public class MainPage {

  // using XPATH regular expression
  @FindBy(how = How.XPATH, using = "//input[contains(@id, 'friendsandfamily')]")
  public WebElement friendsAndFamilyCheckbox;

  // using CSS regular expression
  @FindBy(how = How.CSS, using = "input[id*='SeniorCitizenDiscount']")
  public WebElement seniorCitizenCheckbox;

  // using name property build into Selenium
  @FindBy(how = How.NAME, using = "ctl00$mainContent$chk_IndArm")
  public WebElement indianArmedServicesCheckbox;

  @FindBy(how = How.CSS, using = "input[name='ctl00$mainContent$chk_StudentDiscount']")
  public WebElement studentCheckbox;

  @FindBy(how = How.XPATH, using = "//label[@for='ctl00_mainContent_chk_Unmr']/preceding-sibling::input") // label
  public WebElement unaccompaniedMinorCheckbox;

  @FindBy(how = How.CSS, using = "input[type=checkbox]")
  public List<WebElement> allCheckboxes;


  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
