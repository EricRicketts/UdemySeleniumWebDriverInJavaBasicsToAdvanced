package com.example.assignment5frames;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

  @FindBy(how = How.LINK_TEXT, using = "Nested Frames")
  public WebElement nestedFramesLink;

  @FindBy(how = How.TAG_NAME, using = "frameset")
  public WebElement topLevelFrameset;

  @FindBy(how = How.CSS, using = "frame[name$='frame-top']")
  public WebElement topFrameElementWithinTopLevelFrameset;

  @FindBy(how = How.CSS, using = "frame[name$='frame-middle']")
  public WebElement middleFrameElement;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
