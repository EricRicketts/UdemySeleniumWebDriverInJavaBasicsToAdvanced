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

  @FindBy(how = How.XPATH, using = "(//div[@id='courses-block']//a)[2]")
  public WebElement firstCourseLink;

  @FindBy(how = How.CSS, using = "input.form-control[name$='name']")
  public WebElement nameInput;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
