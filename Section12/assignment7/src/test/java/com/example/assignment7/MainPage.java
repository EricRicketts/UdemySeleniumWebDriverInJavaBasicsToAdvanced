package com.example.assignment7;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.CSS, using = "div.left-align table#product")
  public WebElement courseTable;
  @FindBy(how = How.CSS, using = "div.left-align table#product tr")
  public List<WebElement> courseTableRows;
  @FindBy(how = How.CSS, using = "div.left-align table#product th")
  public List<WebElement> courseTableColumns;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
