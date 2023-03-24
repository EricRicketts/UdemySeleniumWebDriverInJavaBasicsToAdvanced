package com.example.ifameshowtohandle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
  @FindBy(how = How.ID, using = "draggable")
  public WebElement draggableElement;

  @FindBy(how = How.ID, using = "droppable")
  public WebElement droppableElement;

  @FindBy(how = How.CSS, using = "iframe.demo-frame")
  public WebElement iframeElement;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
