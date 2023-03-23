package com.example.assignment4windowhandling;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.CSS, using = "a[href$='/windows']")
  public WebElement multipleWindowsLink;

  @FindBy(how = How.CSS, using = "a[href$='/windows/new']")
  public WebElement childWindowLink;

  @FindBy(how = How.CSS, using = "div.example > h3")
  public WebElement windowH3Element;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
