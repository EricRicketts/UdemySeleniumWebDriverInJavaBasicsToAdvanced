package com.example.ajaxmouseinteractions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.XPATH, using = "//span[contains(text(),'Account & Lists')]")
  public WebElement accountsAndListsElement;

  @FindBy(how = How.CSS, using = "div#nav-flyout-ya-signin > a > span")
  public WebElement popUpSignInButton;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
