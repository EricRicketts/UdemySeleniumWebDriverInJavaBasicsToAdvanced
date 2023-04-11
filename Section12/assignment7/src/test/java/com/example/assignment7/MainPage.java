package com.example.assignment7;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.CSS, using = "div.left-align table#product")
  public WebElement courseTable;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
