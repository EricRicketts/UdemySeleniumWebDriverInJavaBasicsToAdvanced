package com.example.assignment6varioustasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.CSS, using = "div.col-sm-8.right-align")
  public WebElement footerDivContainerElement;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
