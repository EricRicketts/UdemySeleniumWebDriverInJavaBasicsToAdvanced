package com.example.assignment6varioustasks;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.CSS, using = "div.col-sm-8.right-align")
  public WebElement footerDivContainerElement;

  @FindBy(how = How.CSS, using = "div#checkbox-example label")
  public List<WebElement> checkboxLabelElements;

  @FindBy(how = How.CSS, using = "div#checkbox-example input")
  public List<WebElement> checkboxInputElements;

  @FindBy(how = How.ID, using = "dropdown-class-example")
  public WebElement dropdownElement;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
