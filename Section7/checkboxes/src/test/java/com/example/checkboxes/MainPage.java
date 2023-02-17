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

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
