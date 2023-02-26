package com.example.greenkart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.CSS, using = "h4.product-name")
  public List<WebElement> productTitles;

  @FindBy(how = How.XPATH, using = "//button[text()='ADD TO CART']")
  public List<WebElement> addToCartButtons;

  @FindBy(how = How.XPATH, using = "//div[@class='cart-info'] //tr/td[position()=3]")
  public List<WebElement> numberOfItemsAndTotalPrice;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
