package com.example.ifameshowtohandle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.ID, using = "draggable")
  public WebElement draggableElement;

  @FindBy(how = How.ID, using = "droppable")
  public WebElement droppableElement;

  @FindBy(how = How.TAG_NAME, using = "iframe")
  public List<WebElement> iframeElements;

  @FindBy(how = How.CSS, using = "iframe.demo-frame")
  public WebElement iframeElement;

  @FindBy(how = How.LINK_TEXT, using = "Accept")
  public WebElement acceptLink;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
