package com.example.windowhandlingconcepts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.CSS, using = "a.blinkingText")
  public WebElement blinkingTextLink;

  @FindBy(how = How.CSS, using = "p.im-para.red")
  public WebElement desiredUsernameParagraph;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
