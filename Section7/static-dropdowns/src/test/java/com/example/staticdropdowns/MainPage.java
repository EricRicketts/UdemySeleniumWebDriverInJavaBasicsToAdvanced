package com.example.staticdropdowns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.XPATH, using = "a.wt-button_mode_primary")
  public WebElement seeAllToolsButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
