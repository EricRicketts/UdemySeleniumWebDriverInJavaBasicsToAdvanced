package com.example.waitsexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.XPATH, using = "//div[@class='product']")
  public List<WebElement> allProducts;

  @FindBy(how = How.XPATH, using = "//div[@class='product-image']/img[@alt='Walnuts - 1/4 Kg']")
  public WebElement walnuts;

  @FindBy(how = How.XPATH, using = "//div[@class='product-action']/button")
  public List<WebElement> allAddToCartButtons;

  @FindBy(how = How.XPATH, using = "(//div[@class='cart-info'] //tr/td[position()=3])[1]")
  public WebElement cartNumberOfItems;

  @FindBy(how = How.XPATH, using = "(//div[@class='cart-info'] //tr/td[position()=3])[2]")
  public WebElement cartTotalPrice;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
