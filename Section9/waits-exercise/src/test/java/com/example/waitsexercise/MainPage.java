package com.example.waitsexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

// page_url = https://www.jetbrains.com/
public class MainPage {
  @FindBy(how = How.CSS, using = "h4.product-name")
  public List<WebElement> allProductNames;

  @FindBy(how = How.XPATH, using = "//div[@class='product-image']/img[@alt='Walnuts - 1/4 Kg']")
  public WebElement walnuts;

  @FindBy(how = How.XPATH, using = "//div[@class='product-action']/button")
  public List<WebElement> allAddToCartButtons;

  @FindBy(how = How.XPATH, using = "//div[@class='cart-info'] //tr/td[position()=3]")
  public List<WebElement> numberOfItemsAndTotalPrice;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
