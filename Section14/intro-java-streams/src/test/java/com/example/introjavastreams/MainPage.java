package com.example.introjavastreams;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.CSS, using = "div#gf-BIG")
  public WebElement footerElement;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
