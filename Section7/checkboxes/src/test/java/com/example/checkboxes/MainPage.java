package com.example.checkboxes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.CSS, using = "input#ctl00_mainContent_chk_friendsandfamily")
  public WebElement friendsAndFamilyCheckbox;

  @FindBy(how = How.XPATH, using = "//input[@id='ctl00_mainContent_chk_SeniorCitizenDiscount']")
  public WebElement seniorCitizenCheckbox;

  @FindBy(how = How.NAME, using = "ctl00$mainContent$chk_IndArm") // name
  public WebElement indianArmedServicesCheckbox;

  @FindBy(how = How.CSS, using = "input[name='ctl00$mainContent$chk_StudentDiscount']")
  public WebElement studentCheckbox;

  @FindBy(how = How.XPATH, using = "//label[@for='ctl00_mainContent_chk_Unmr']/preceding-sibling::input") // label
  public WebElement unaccompaniedMinorCheckbox;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
