package com.example.multipletabswindows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.TAG_NAME, using = "footer")
  public WebElement mainPageFooter;

  @FindBy(how = How.CSS, using = "div.copyright")
  public WebElement newTabFooter;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
