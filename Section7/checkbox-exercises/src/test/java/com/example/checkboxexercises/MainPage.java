package com.example.checkboxexercises;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.ID, using = "checkBoxOption1")
  public WebElement checkBoxOptionOne;

  @FindBy(how = How.NAME, using = "checkBoxOption2")
  public WebElement checkBoxOptionTwo;

  @FindBy(how = How.XPATH, using = "//label[@for='honda']/input")
  public WebElement checkBoxOptionThree;

  @FindBy(how = How.XPATH, using = "//input[@type='checkbox']")
  public List<WebElement> allCheckBoxOptions;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
