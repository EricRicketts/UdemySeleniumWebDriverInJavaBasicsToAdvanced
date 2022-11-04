package com.example.identifyelementwithidandnamelocators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {

  @FindBy(how = How.ID, using = "inputUsername")
  public WebElement inputUserName;

  @FindBy(how = How.NAME, using = "inputPassword")
  public WebElement inputPassword;

  @FindBy(how = How.CSS, using = "button.submit.signInBtn")
  public WebElement signInButton;

  @FindBy(how = How.CSS, using = "p.error")
  public WebElement errorParagraph;

/*

  @FindBy(css = "a.wt-button_mode_primary")
  public WebElement seeAllToolsButton;

  @FindBy(xpath = "//div[@data-test='main-menu-item' and @data-test-marker = 'Developer Tools']")
  public WebElement toolsMenu;

  @FindBy(css = "[data-test='site-header-search-action']")
  public WebElement searchButton;

*/

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
