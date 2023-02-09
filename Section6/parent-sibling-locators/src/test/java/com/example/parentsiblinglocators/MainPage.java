package com.example.parentsiblinglocators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.XPATH, using = "//header/div/button[1]")
  WebElement practiceButtonWithXpath;

  @FindBy(how = How.XPATH, using = "//header/div/button[1]/following-sibling::button[1]")
  WebElement loginButtonWithXpathSibling;

  @FindBy(how = How.XPATH, using = "//button[text()='Practice']/parent::div/parent::header")
  WebElement headerParentStartingFromPracticeButton;

  @FindBy(how = How.XPATH, using = "//header/div/button[1]/parent::div/button[3]")
  WebElement signUpButtonGoingUpAndDownElementHierarchy;

  @FindBy(how = How.CSS, using = "header > div > button:nth-of-type(1)")
  WebElement practiceButtonWithCss;

  @FindBy(how = How.CSS, using = "header > div > button:nth-of-type(1) + button")
  WebElement loginButtonWithCssSibling;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
