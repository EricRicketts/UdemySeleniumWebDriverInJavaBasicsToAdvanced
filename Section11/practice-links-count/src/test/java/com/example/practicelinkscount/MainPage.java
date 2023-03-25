package com.example.practicelinkscount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.TAG_NAME, using = "a")
  public List<WebElement> allLinkElements;

  @FindBy(how = How.ID, using = "gf-BIG")
  public WebElement footerDivElement;

  @FindBy(how = How.CSS, using = "div#gf-BIG > table > tbody > tr > td:first-of-type")
  public WebElement firstColumnOfFooterTable;

  @FindBy(how = How.TAG_NAME, using = "h1")
  public WebElement h1Element;

  @FindBy(how = How.CSS, using = "a[href*='medianh']")
  public WebElement copyrightLink;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
